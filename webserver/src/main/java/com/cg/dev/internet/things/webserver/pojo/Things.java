package com.cg.dev.internet.things.webserver.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Things in internet
 */
public class Things {
    private String thingsName;
    private String thingsId;
    private Logger logger = LoggerFactory.getLogger(Things.class);
    public String getThingsName() {
        return thingsName;
    }

    public void setThingsName(String thingsName) {
        this.thingsName = thingsName;
        logger.debug("things name set:{}",thingsName);
    }

    public String getThingsId() {
        return thingsId;
    }

    public void setThingsId(String thingsId) {
        this.thingsId = thingsId;
    }
}
