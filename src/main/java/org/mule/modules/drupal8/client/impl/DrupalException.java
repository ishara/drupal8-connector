package org.mule.modules.drupal8.client.impl;

public class DrupalException extends RuntimeException
{
    private static final long serialVersionUID = 3597388596862369136L;

    public DrupalException(String message)
    {
        super(message);
    }

    public DrupalException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
