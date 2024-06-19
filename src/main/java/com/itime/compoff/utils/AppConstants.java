package com.itime.compoff.utils;



public class AppConstants {


    public static final String COMP_OFF_STATUS_UPDATED = "Comp Off status updated";

    public static final int MIN_WORK_HOUR_COMPOFF = 4;
    public static final int MIN_WORK_HOUR_FULL_DAY_COMPOFF = 8;

    public static final String LEAVE_TYPE_NOT_FOUND = "Leave Type Not Found";

    public static final String ERROR = "ERROR";
    public static final String COMPOFF_EXPIRY_NOTIFICATION_EMAIL_TEMPLATE =
            """
                    <html>
                    <head>
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                background-color: #f7f7f7;
                                color: #333;
                                padding: 20px;
                                margin: 0;
                            }
                            .container {
                                background-color: #fff;
                                padding: 20px;
                                border-radius: 8px;
                                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                                max-width: 600px;
                                margin: auto;
                            }
                            h1 {
                                color: #4CAF50;
                            }
                            p {
                                line-height: 1.6;
                            }
                            .button {
                                display: inline-block;
                                padding: 10px 20px;
                                margin: 20px 0;
                                font-size: 16px;
                                color: #fff;
                                background-color: #4CAF50;
                                border: none;
                                border-radius: 5px;
                                text-decoration: none;
                            }
                            .footer {
                                text-align: center;
                                font-size: 12px;
                                color: #888;
                                margin-top: 20px;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <h1>CompOff Expiry Notification</h1>
                            <p>Dear Employee,</p>
                            <p>This is a notification that your compensatory off requested on <strong>%s</strong> is expiring soon.</p>
                            <p><strong>Work hours:</strong> %s</p>
                            <p><strong>Reason:</strong> %s</p>
                            <p>Please take necessary action to utilize your compensatory off before it expires.</p>
                            <a href="" class="button">Take Action Now</a>
                        </div>
                    </body>
                    </html>
                    """;

    public static final String LEAVE_STATUS_UPDATED = "Leave Status Updated";
    public static final String ALREADY_LEAVE_REQUEST_APPLIED = "Already Leave applied for this date";
    public static final String LEAVE_UNAVAILABLE = "Leave Unavailable";
    public static final String PARSER_EXCEPTION = "Parse Exception";
    public static final String EMAIL = "aakash.b@aaludra.com";
    public static final String DEFAULT_COMP_OFF_LEAVE_CODE = "COMP_OFF";

    private AppConstants() {
    }
}
