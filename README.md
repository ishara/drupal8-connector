
Mule Cloud Connector for the Drupal 8 Core Rest APIs
----------------------------------------------------

## Requirements:

### Modules:
For this connector to work, at a minimum you must have a Drupal 8 installation with the following modules setup:

• REST
• Serialization 
• HAL
• HTTP Basic Authententication

You can do this by navigating to http://${YOUR_DRUPAL}/admin/modules and enabling them.

### Settings:
Once enabled you need to setup the API configuration.

This can be done by navigating to:

http://${YOUR_DRUPAL}/admin/config/development/configuration/single/import

And importing you rest.settings config similar to the following:

    resources:
      'entity:node':
        GET:
          supported_auth:
            - basic_auth
            - cookie
          supported_formats:
            - json
            - hal_json
        POST:
          supported_formats:
            - json
            - hal_json
          supported_auth:
            - basic_auth
            - cookie
        DELETE:
          supported_auth:
            - basic_auth
            - cookie
          supported_formats:
            - json
            - hal_json
        PATCH:
          supported_auth:
            - basic_auth
            - cookie
          supported_formats:
            - json
            - hal_json

![](https://github.com/ryandcarter/drupal8-connector/blob/master/doc/setting-import.png)

The connector supports both JSON and HAL formats, However for POST requests; hal_json must be enabled as Drupal only supports creating resources with this media type.
 
Licence
----------------------------
This project is distributed under [Apache 2 licence](http://www.apache.org/licenses/LICENSE-2.0.html). 
