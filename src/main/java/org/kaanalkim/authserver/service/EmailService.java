package org.kaanalkim.authserver.service;

import org.kaanalkim.authserver.payload.request.EmailDetails;
import org.thymeleaf.context.Context;

public interface EmailService {

    void send(EmailDetails emailDetails);

    void sendWithTemplate(EmailDetails emailDetails, String templateName, Context context);
}
