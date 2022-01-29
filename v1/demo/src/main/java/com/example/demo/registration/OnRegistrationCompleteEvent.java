// This class handles the event that the controller is sending out

package com.example.demo.registration;

import java.util.Locale;

import com.example.demo.model.ModelUser;
import org.springframework.context.ApplicationEvent;

import lombok.*;


// @SuppressWarnings("serial")
@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final ModelUser user;

    public OnRegistrationCompleteEvent(final ModelUser user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}

