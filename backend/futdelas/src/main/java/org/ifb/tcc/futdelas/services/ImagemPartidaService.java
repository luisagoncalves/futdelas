package org.ifb.tcc.futdelas.services;

import jakarta.enterprise.context.ApplicationScoped;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.ifb.tcc.futdelas.entities.Partida;
import org.ifb.tcc.futdelas.entities.Time;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

@ApplicationScoped
public class ImagemPartidaService {

    private static final int IMAGE_WIDTH = 800;
    private static final int IMAGE_HEIGHT = 400;
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color APP_NAME_COLOR = new Color(100, 200, 255);
    private static final Font TEAM_FONT = new Font("SansSerif", Font.BOLD, 40);
    private static final Font TIME_FONT = new Font("SansSerif", Font.PLAIN, 30);
    private static final Font APP_FONT = new Font("SansSerif", Font.BOLD, 24);
    private static final int LOGO_SIZE = 150;
    private static final int LOGO_Y_POSITION = 100;

    public byte[] generateMatchImage(Partida partida) throws IOException {
        BufferedImage image = createBaseImage();
        Graphics2D g = image.createGraphics();

        // Configurações de qualidade
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Baixar e desenhar escudos
        BufferedImage homeLogo = downloadTeamLogo(partida.getTimeMandante().getEscudo());
        BufferedImage awayLogo = downloadTeamLogo(partida.getTimeVisitante().getEscudo());

        // Desenhar informações dos times
        drawTeamInfo(g, partida.getTimeMandante(), homeLogo, 100, "Mandante");
        drawTeamInfo(g, partida.getTimeVisitante(), awayLogo, 550, "Visitante");

        // Desenhar informações da partida
        drawMatchInfo(g, partida);

        // Desenhar nome do app
        drawAppName(g);

        g.dispose();
        return convertToByteArray(image);
    }

    private BufferedImage downloadTeamLogo(String logoUrl) throws IOException {
        HttpURLConnection connection = configureConnection(new URL(logoUrl));
        try (InputStream inputStream = connection.getInputStream()) {
            if (logoUrl.endsWith(".svg")) {
                return convertSvgToPng(inputStream, LOGO_SIZE, LOGO_SIZE);
            } else {
                BufferedImage originalImage = ImageIO.read(inputStream);
                if (originalImage == null) {
                    throw new IOException("Formato de imagem não suportado: " + logoUrl);
                }
                return resizeLogo(originalImage, LOGO_SIZE, LOGO_SIZE);
            }
        } finally {
            connection.disconnect();
        }
    }

    private BufferedImage convertSvgToPng(InputStream svgInputStream, int width, int height) throws IOException {
        try {
            // Configuração do transcoder
            PNGTranscoder transcoder = new PNGTranscoder();

            // Definir dimensões de saída
            transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) width);
            transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) height);

            // Configurar entrada e saída
            TranscoderInput input = new TranscoderInput(svgInputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);

            // Realizar a conversão
            transcoder.transcode(input, output);

            // Ler o PNG resultante
            byte[] pngBytes = outputStream.toByteArray();
            try (ByteArrayInputStream bais = new ByteArrayInputStream(pngBytes)) {
                return ImageIO.read(bais);
            }
        } catch (Exception e) {
            throw new IOException("Falha ao converter SVG para PNG", e);
        }
    }

    private HttpURLConnection configureConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        connection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(10));
        connection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(30));
        return connection;
    }

    private BufferedImage resizeLogo(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }

    private BufferedImage createBaseImage() {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        g.dispose();
        return image;
    }

    private void drawTeamInfo(Graphics2D g, Time time, BufferedImage logo, int xPos, String tipoTime) {
        // Desenhar nome do time
        g.setColor(TEXT_COLOR);
        g.setFont(TEAM_FONT);
        g.drawString(time.getNomePopular(), xPos, 90);

        // Desenhar escudo
        if (logo != null) {
            g.drawImage(logo, xPos, LOGO_Y_POSITION, null);
        } else {
            // Fallback caso o download falhe
            g.setColor(Color.GRAY);
            g.fillOval(xPos, LOGO_Y_POSITION, LOGO_SIZE, LOGO_SIZE);
            g.setColor(TEXT_COLOR);
            g.drawString(time.getSigla(), xPos + 50, LOGO_Y_POSITION + 80);
        }

        // Desenhar tipo (mandante/visitante)
        g.setFont(new Font("SansSerif", Font.ITALIC, 20));
        g.drawString(tipoTime, xPos, LOGO_Y_POSITION + LOGO_SIZE + 30);
    }

    private void drawMatchInfo(Graphics2D g, Partida partida) {
        String matchInfo = String.format("%s - %s", partida.getDataRealizacao(), partida.getHoraRealizacao());

        g.setColor(TEXT_COLOR);
        g.setFont(TIME_FONT);

        // Centralizar texto
        int textWidth = g.getFontMetrics().stringWidth(matchInfo);
        int xPos = (IMAGE_WIDTH - textWidth) / 2;

        g.drawString(matchInfo, xPos, 220);

        // Status da partida
        String status = "Status: " + translateStatus(partida.getStatus());
        textWidth = g.getFontMetrics().stringWidth(status);
        xPos = (IMAGE_WIDTH - textWidth) / 2;

        g.drawString(status, xPos, 260);
    }

    private void drawAppName(Graphics2D g) {
        String appName = "FutDelas";
        g.setFont(APP_FONT);
        g.setColor(APP_NAME_COLOR);

        int textWidth = g.getFontMetrics().stringWidth(appName);
        int xPos = (IMAGE_WIDTH - textWidth) / 2;

        g.drawString(appName, xPos, 350);
    }

    private String translateStatus(String status) {
        return switch (status.toLowerCase()) {
            case "pre-jogo" -> "Pré-Jogo";
            case "ao-vivo" -> "Ao Vivo";
            case "encerrado" -> "Encerrado";
            default -> status;
        };
    }

    private byte[] convertToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        return baos.toByteArray();
    }
}