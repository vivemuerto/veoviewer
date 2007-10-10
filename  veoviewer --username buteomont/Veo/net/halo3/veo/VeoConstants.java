/*
 * VeoConstants.java
 * 
 * Created on Aug 13, 2004
 *
 */
package net.halo3.veo;

/**
 * Listing of all the VeoConstants, including the required JPEG Header.
 * 
 * Special thanks for Tobias Hoellrich for help discovering the protocol and
 * required details.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: VeoConstants.java,v 1.2 2004/08/16 01:47:33 jr Exp $
 */
public interface VeoConstants {
    static int JPG_HEADER[] = { 0xFF, 0xD8, 0xFF, 0xE0, 0x00, 0x11, 0x00, 0x4A,
            0x46, 0x49, 0x46, 0x00, 0x01, 0x02, 0x00, 0x00, 0x48, 0x00, 0x48,
            0x00, 0x00, 0xFF, 0xDB, 0x00, 0x84, 0x00, 0x08, 0x05, 0x06, 0x07,
            0x06, 0x05, 0x08, 0x07, 0x06, 0x07, 0x09, 0x08, 0x08, 0x09, 0x0D,
            0x15, 0x0E, 0x0D, 0x0C, 0x0C, 0x0D, 0x1A, 0x11, 0x12, 0x0E, 0x15,
            0x20, 0x1A, 0x20, 0x20, 0x1C, 0x1A, 0x1C, 0x1C, 0x20, 0x25, 0x2F,
            0x27, 0x20, 0x22, 0x2F, 0x22, 0x1C, 0x1C, 0x2B, 0x39, 0x2B, 0x2F,
            0x33, 0x33, 0x33, 0x39, 0x33, 0x20, 0x27, 0x39, 0x40, 0x39, 0x33,
            0x40, 0x2F, 0x33, 0x33, 0x33, 0x01, 0x08, 0x09, 0x09, 0x0D, 0x0B,
            0x0D, 0x17, 0x0E, 0x0E, 0x1C, 0x33, 0x22, 0x1C, 0x22, 0x33, 0x33,
            0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33,
            0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33,
            0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33,
            0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33,
            0x33, 0x33, 0x33, 0x33, 0xFF, 0xC0, 0x00, 0x11, 0x08, 0x00, 0xF0,
            0x01, 0x40, 0x03, 0x01, 0x21, 0x00, 0x02, 0x11, 0x01, 0x03, 0x11,
            0x01, 0xFF, 0xC4, 0x00, 0xD2, 0x00, 0x00, 0x01, 0x05, 0x01, 0x01,
            0x01, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A,
            0x0B, 0x10, 0x00, 0x02, 0x01, 0x03, 0x03, 0x02, 0x04, 0x03, 0x05,
            0x05, 0x04, 0x04, 0x00, 0x00, 0x01, 0x7D, 0x01, 0x02, 0x03, 0x00,
            0x04, 0x11, 0x05, 0x12, 0x21, 0x31, 0x41, 0x06, 0x13, 0x51, 0x61,
            0x07, 0x22, 0x71, 0x14, 0x32, 0x81, 0x91, 0xA1, 0x08, 0x23, 0x42,
            0xB1, 0xC1, 0x15, 0x52, 0xD1, 0xF0, 0x24, 0x33, 0x62, 0x72, 0x82,
            0x09, 0x0A, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x25, 0x26, 0x27, 0x28,
            0x29, 0x2A, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3A, 0x43, 0x44,
            0x45, 0x46, 0x47, 0x48, 0x49, 0x4A, 0x53, 0x54, 0x55, 0x56, 0x57,
            0x58, 0x59, 0x5A, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A,
            0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A, 0x83, 0x84, 0x85,
            0x86, 0x87, 0x88, 0x89, 0x8A, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97,
            0x98, 0x99, 0x9A, 0xA2, 0xA3, 0xA4, 0xA5, 0xA6, 0xA7, 0xA8, 0xA9,
            0xAA, 0xB2, 0xB3, 0xB4, 0xB5, 0xB6, 0xB7, 0xB8, 0xB9, 0xBA, 0xC2,
            0xC3, 0xC4, 0xC5, 0xC6, 0xC7, 0xC8, 0xC9, 0xCA, 0xD2, 0xD3, 0xD4,
            0xD5, 0xD6, 0xD7, 0xD8, 0xD9, 0xDA, 0xE1, 0xE2, 0xE3, 0xE4, 0xE5,
            0xE6, 0xE7, 0xE8, 0xE9, 0xEA, 0xF1, 0xF2, 0xF3, 0xF4, 0xF5, 0xF6,
            0xF7, 0xF8, 0xF9, 0xFA, 0xFF, 0xDA, 0x00, 0x0C, 0x03, 0x01, 0x00,
            0x02, 0x00, 0x03, 0x00, 0x00, 0x3F, 0x00 };

    final int VEO_RATE_DELAYS[] = { 0x0f4240, 0x07a120, 0x051615, 0x03d090,
            0x030d40, 0x028b0a, 0x022e09, 0x01e848, 0x01b207, 0x0186a0 };

    final byte VEO_MSG_LOGON = 0x00;

    final byte VEO_STREAM_160x120 = 0x00;

    final byte VEO_STREAM_320x240 = 0x01;

    final byte VEO_STREAM_640x480 = 0x02;

    final byte VEO_MSG_STREAM_START = 0x01;

    final byte VEO_MSG_STREAM_STOP = 0x02;

    final byte VEO_MSG_LOCATENET = 0x03; // not implemented

    final byte VEO_MSG_SETUPNET = 0x04; // not implemented

    final byte VEO_MSG_GETBRIGHT = 0x05;

    final byte VEO_MSG_SETBRIGHT = 0x06;

    final byte VEO_MSG_GETLIGHT = 0x07;

    final byte VEO_MSG_SETLIGHT = 0x08;

    final byte VEO_MSG_GETCAMINFO = 0x09;

    final byte VEO_MSG_SETCAMINFO = 0x0a; // not implemented

    final byte VEO_MSG_GETEMAILPROP = 0x0b; // not implemented

    final byte VEO_MSG_SETEMAILPROP = 0x0c; // not implemented

    final byte VEO_MSG_GETUSERACCOUNTS = 0x0d; // not implemented

    final byte VEO_MSG_SETUSERACCOUNT = 0x0e; // not implemented

    final byte VEO_MSG_DELETEUSER = 0x0f; // not implemented

    final byte VEO_MSG_REPLACEFIRMLEN = 0x10; // not implemented

    final byte VEO_MSG_REPLACEFIRMDATA = 0x11; // not implemented

    final byte VEO_MSG_GETMOTIONDETECT = 0x12; // not implemented

    final byte VEO_MSG_SETMOTIONDETECT = 0x13; // not implemented

    final byte VEO_MSG_SELECT_STREAM = 0x14;

    final byte VEO_MSG_MOVE = 0x15;

    final byte VEO_MSG_RESET = 0x16;

    final byte VEO_MSG_GETSTATUSLIGHT = 0x17;

    final byte VEO_MSG_SETSTATUSLIGHT = 0x18;

    final byte VEO_RESPONSE_BEGIN_IMAGE = 0x00;

    final byte VEO_RESPONSE_CONTINUE_IMAGE = 0x01;

    final byte VEO_RESPONSE_END_IMAGE = 0x02;

    final byte VEO_RESPONSE_OK = 0x04;

    final byte VEO_RESPONSE_ERROR = 0x05;

    final byte VEO_MOVE_UP = 0x00;

    final byte VEO_MOVE_FULL_UP = 0x01;

    final byte VEO_MOVE_DOWN = 0x02;

    final byte VEO_MOVE_FULL_DOWN = 0x03;

    final byte VEO_MOVE_LEFT = 0x04;

    final byte VEO_MOVE_FULL_LEFT = 0x05;

    final byte VEO_MOVE_RIGHT = 0x06;

    final byte VEO_MOVE_FULL_RIGHT = 0x07;
}