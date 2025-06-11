package org.ifb.tcc.futdelas.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.ifb.tcc.futdelas.entities.Partida;
import org.ifb.tcc.futdelas.entities.Time;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

@ApplicationScoped
public class ImagemPartidaService {

    // Configurações de design
    private static final int IMAGE_WIDTH = 900;
    private static final int IMAGE_HEIGHT = 500;
    private static final Color BACKGROUND_COLOR = new Color(18, 18, 18);
    private static final Color PRIMARY_COLOR = new Color(0, 150, 255);
    private static final Color SECONDARY_COLOR = new Color(240, 240, 240);
    private static final Color ACCENT_COLOR = new Color(255, 100, 100);
    
    private static final Font TEAM_FONT = new Font("Montserrat", Font.BOLD, 42);
    private static final Font INFO_FONT = new Font("Montserrat", Font.PLAIN, 24);
    private static final Font STATUS_FONT = new Font("Montserrat", Font.BOLD, 28);
    private static final Font APP_FONT = new Font("Montserrat", Font.BOLD, 32);
    
    private static final int LOGO_SIZE = 140;
    private static final int PADDING = 50;
    private static final int ELEMENT_SPACING = 30;

    public byte[] gerarImagemPartida(Partida partida) throws IOException {
        BufferedImage imagem = criarImagemBase();
        Graphics2D g = imagem.createGraphics();
        configurarQualidadeGraficos(g);

        // Carregar escudos
        BufferedImage escudoMandante = baixarEscudoTime(partida.getTimeMandante().getEscudo());
        BufferedImage escudoVisitante = baixarEscudoTime(partida.getTimeVisitante().getEscudo());

        desenharCabecalho(g);
        desenharTimes(g, partida, escudoMandante, escudoVisitante);
        desenharInfoPartida(g, partida);
        desenharRodape(g);

        g.dispose();
        return converterParaBytes(imagem);
    }

    private BufferedImage criarImagemBase() {
        BufferedImage imagem = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = imagem.createGraphics();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        g.dispose();
        return imagem;
    }

    private void configurarQualidadeGraficos(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    }

    private BufferedImage baixarEscudoTime(String urlEscudo) throws IOException {
        if (urlEscudo == null || urlEscudo.isEmpty()) return null;

        HttpURLConnection conexao = configurarConexao(new URL(urlEscudo));
        try (InputStream inputStream = conexao.getInputStream()) {
            return urlEscudo.endsWith(".svg") 
                ? converterSvgParaPng(inputStream) 
                : redimensionarImagem(ImageIO.read(inputStream));
        } finally {
            conexao.disconnect();
        }
    }

    private BufferedImage converterSvgParaPng(InputStream svgInput) throws IOException {
        try {
            PNGTranscoder transcoder = new PNGTranscoder();
            transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) LOGO_SIZE);
            transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) LOGO_SIZE);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            transcoder.transcode(new TranscoderInput(svgInput), new TranscoderOutput(outputStream));

            try (ByteArrayInputStream bais = new ByteArrayInputStream(outputStream.toByteArray())) {
                return ImageIO.read(bais);
            }
        } catch (Exception e) {
            throw new IOException("Falha na conversão SVG para PNG", e);
        }
    }

    private BufferedImage redimensionarImagem(BufferedImage imagemOriginal) {
        if (imagemOriginal == null) return null;

        BufferedImage imagemRedimensionada = new BufferedImage(LOGO_SIZE, LOGO_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = imagemRedimensionada.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(imagemOriginal, 0, 0, LOGO_SIZE, LOGO_SIZE, null);
        g.dispose();
        return imagemRedimensionada;
    }

    private HttpURLConnection configurarConexao(URL url) throws IOException {
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestProperty("User-Agent", "FutDelas/1.0");
        conexao.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(10));
        conexao.setReadTimeout((int) TimeUnit.SECONDS.toMillis(30));
        return conexao;
    }

    private void desenharCabecalho(Graphics2D g) {
        GradientPaint gradient = new GradientPaint(0, 0, PRIMARY_COLOR, IMAGE_WIDTH, 0, new Color(0, 100, 200));
        g.setPaint(gradient);
        g.fillRect(0, 0, IMAGE_WIDTH, 80);

        g.setColor(Color.WHITE);
        g.setFont(APP_FONT);
        String titulo = "PRÓXIMA PARTIDA";
        int larguraTexto = g.getFontMetrics().stringWidth(titulo);
        g.drawString(titulo, (IMAGE_WIDTH - larguraTexto) / 2, 50);
    }

    private void desenharTimes(Graphics2D g, Partida partida, BufferedImage escudoMandante, BufferedImage escudoVisitante) {
        int yPos = 150;
        
        desenharTime(g, partida.getTimeMandante(), escudoMandante, PADDING, yPos);
        
        g.setColor(SECONDARY_COLOR);
        g.setFont(new Font("Montserrat", Font.BOLD, 36));
        g.drawString("×", IMAGE_WIDTH / 2 - 15, yPos + LOGO_SIZE / 2 + 20);
        
        desenharTime(g, partida.getTimeVisitante(), escudoVisitante, IMAGE_WIDTH - PADDING - LOGO_SIZE, yPos);
    }

    private void desenharTime(Graphics2D g, Time time, BufferedImage escudo, int xPos, int yPos) {
        if (escudo != null) {
            g.drawImage(escudo, xPos, yPos, null);
        } else {
            g.setColor(new Color(40, 40, 40));
            g.fillRoundRect(xPos, yPos, LOGO_SIZE, LOGO_SIZE, 20, 20);
            g.setColor(SECONDARY_COLOR);
            g.drawString(time.getSigla(), xPos + LOGO_SIZE/2 - 20, yPos + LOGO_SIZE/2 + 10);
        }

        g.setColor(SECONDARY_COLOR);
        g.setFont(TEAM_FONT);
        String nomeTime = time.getNomePopular();
        int textWidth = g.getFontMetrics().stringWidth(nomeTime);
        g.drawString(nomeTime, xPos + (LOGO_SIZE - textWidth)/2, yPos - 20);
    }

    private void desenharInfoPartida(Graphics2D g, Partida partida) {
        int yPos = 350;
        
        String infoPartida = String.format("%s • %s", partida.getDataRealizacao(), partida.getHoraRealizacao());
        g.setColor(SECONDARY_COLOR);
        g.setFont(INFO_FONT);
        int textWidth = g.getFontMetrics().stringWidth(infoPartida);
        g.drawString(infoPartida, (IMAGE_WIDTH - textWidth)/2, yPos);
        
        String status = traduzirStatus(partida.getStatus());
        g.setColor(ACCENT_COLOR);
        g.setFont(STATUS_FONT);
        textWidth = g.getFontMetrics().stringWidth(status);
        g.drawString(status, (IMAGE_WIDTH - textWidth)/2, yPos + 40);
    }

    private void desenharRodape(Graphics2D g) {
        g.setColor(new Color(30, 30, 30, 200));
        g.fillRect(0, IMAGE_HEIGHT - 50, IMAGE_WIDTH, 50);
        
        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("Montserrat", Font.PLAIN, 14));
        String rodape = "FutDelas • Acompanhe seu time do coração";
        int textWidth = g.getFontMetrics().stringWidth(rodape);
        g.drawString(rodape, (IMAGE_WIDTH - textWidth)/2, IMAGE_HEIGHT - 20);
    }

    private String traduzirStatus(String status) {
        return switch (status.toLowerCase()) {
            case "pre-jogo" -> "▶ PRÉ-JOGO";
            case "ao-vivo" -> "● AO VIVO";
            case "encerrado" -> "✔ ENCERRADO";
            default -> status.toUpperCase();
        };
    }

    private byte[] converterParaBytes(BufferedImage imagem) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem, "PNG", baos);
        return baos.toByteArray();
    }
}