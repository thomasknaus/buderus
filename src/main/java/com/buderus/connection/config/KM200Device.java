package com.buderus.connection.config;

/*
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 * <p>
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 * <p>
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * <p>
 * SPDX-License-Identifier: EPL-2.0
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class was taken from the OpenHAB 1.x Buderus / KM200 binding, and modified to run without the OpenHAB infrastructure.
 *
 * The KM200Device representing the device with its all capabilities
 *
 * @author Markus Eckhardt
 * @since 1.9.0
 */

public class KM200Device
{

    private static final Logger logger = LoggerFactory.getLogger(KM200Device.class);

    /* valid IPv4 address of the KMxxx. */
    protected String ip4Address = null;

    /* The returned device charset for communication */
    protected String charSet = null;

    /* Buderus_MD5Salt */
    protected byte[] MD5Salt = null;

    /* Is the first INIT done */
    protected Boolean inited = false;


    // getter
    public String getIP4Address()
    {
        return ip4Address;
    }


    public String getCharSet()
    {
        return charSet;
    }

    // setter
    public void setIP4Address(String ip)
    {
        ip4Address = ip;
    }

    public byte[] getMD5Salt(){
        return MD5Salt;
    }

    public void setMD5Salt(String salt)
    {
        MD5Salt = DatatypeConverter.parseHexBinary(salt);
    }

    public void setCharSet(String charset)
    {
        charSet = charset;
    }

    public void setInited(Boolean Init)
    {
        inited = Init;
    }
}