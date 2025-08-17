package org.cfs.BootP01;

import org.springframework.stereotype.Service;

@Service
public class EmailService implements MessageService
{
    public String getEmail()
    {
        return "new email recived ";
    }
}
