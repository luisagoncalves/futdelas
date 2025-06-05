package org.ifb.tcc.futdelas.controllers;

import org.ifb.tcc.futdelas.dtos.NotificationRequest;
import org.ifb.tcc.futdelas.services.NotificationService;

import com.google.firebase.messaging.FirebaseMessagingException;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    NotificationService notificationService;

    @POST
    @Path("/device")
    public Response sendToDevice(NotificationRequest request) {
        try {
            String response = notificationService.sendNotificationToDevice(
                request.getDeviceToken(),
                request.getTitle(),
                request.getBody()
            );
            return Response.ok(response).build();
        } catch (FirebaseMessagingException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}