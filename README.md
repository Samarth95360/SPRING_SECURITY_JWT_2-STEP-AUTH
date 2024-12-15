# SPRING_SECURITY_JWT_2-STEP-AUTH
CREATED A SPRING SECURITY STATELESS API FOR JWT USER AUTHENTICATION AND AUTHORIZATION WITH 2-STEP-AUTH AND PASSWORD VALIDATOR WITH EMAIL VERIFICATION ALONG WITH MULTIPLE ATTEMPT ACCOUNT LOGOUT FOR BETTER SECURITY

# END POINTS INCLUDED ARE 

For user Registration :- /auth/register
For user Login :- /auth/login
for user otp-verification :- /auth/otp-matcher?otp=

# User Registration Model 
{
    "fullName" : "",
    "email" : "",
    "password" : "",
    "role" : ""
}

# User Login Model
{
    "email" : "",
    "password" : ""
}

# Now Login Returns a Temp JWT Token Valid for only 5 min and an Otp in Your Registered Email.
# You Have to pass that Temp JWT along with Otp to get an Actual Jwt for auth task to be done.
