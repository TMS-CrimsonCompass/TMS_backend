# TMS_backend
After cloning the backend project into our system follow the steps below to complete the setup

1. make the necessary changes (datasource username and password) in the application properties file

2. in the resources folder create a application-secret.properties file and include the below two lines 

   jwt.secret= "your-256-bit-secret"

   jwt.expiration=86400000


   the secret key can be generated using this in commad prompt:

   @for /f "delims=" %a in ('openssl rand -base64 64') do @echo %a



   