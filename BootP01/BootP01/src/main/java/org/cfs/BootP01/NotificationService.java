package org.cfs.BootP01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationService
{
    @Autowired
    private MessageService service;

    public NotificationService()
    {

    }

    public NotificationService(MessageService service) {
        this.service = service;
    }

    public MessageService getService()
    {
        return service;
    }

    public void setService(MessageService service) {
        this.service = service;
    }

    public void pushService()
    {
        System.out.println(service.getEmail());
    }
}
