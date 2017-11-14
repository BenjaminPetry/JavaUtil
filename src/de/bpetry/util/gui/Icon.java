/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util.gui;

import de.bpetry.util.Log;
import java.net.URL;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;

/**
 * Uses AwesomeFont to present icons. Inspired from
 * http://www.jensd.de/wordpress/?p=132. To use icons, you need to put the
 * fontawesome-webfont.ttf into your jar file. You can download it here:
 * https://github.com/FortAwesome/Font-Awesome/tree/master/fonts,
 * http://fontawesome.io/
 *
 * @author Benjamin Petry
 */
public class Icon
{

    //-------------------------------------------------------------------------
    ////////////////////////////////  Constants ///////////////////////////////
    //-------------------------------------------------------------------------
    public static final String GLASS = "\uf000";
    public static final String MUSIC = "\uf001";
    public static final String SEARCH = "\uf002";
    public static final String ENVELOPE = "\uf003";
    public static final String HEART = "\uf004";
    public static final String STAR = "\uf005";
    public static final String STAR_EMPTY = "\uf006";
    public static final String USER = "\uf007";
    public static final String FILM = "\uf008";
    public static final String TH_LARGE = "\uf009";
    public static final String TH = "\uf00a";
    public static final String TH_LIST = "\uf00b";
    public static final String OK = "\uf00c";
    public static final String REMOVE = "\uf00d";
    public static final String ZOOM_IN = "\uf00e";
    public static final String ZOOM_OUT = "\uf010";
    public static final String OFF = "\uf011";
    public static final String SIGNAL = "\uf012";
    public static final String COG = "\uf013";
    public static final String TRASH = "\uf014";
    public static final String HOME = "\uf015";
    public static final String FILE = "\uf016";
    public static final String TIME = "\uf017";
    public static final String ROAD = "\uf018";
    public static final String DOWNLOAD_ALT = "\uf019";
    public static final String DOWNLOAD = "\uf01a";
    public static final String UPLOAD = "\uf01b";
    public static final String INBOX = "\uf01c";
    public static final String PLAY_CIRCLE = "\uf01d";
    public static final String REPEAT = "\uf01e";
    public static final String REFRESH = "\uf021";
    public static final String LIST_ALT = "\uf022";
    public static final String LOCK = "\uf023";
    public static final String FLAG = "\uf024";
    public static final String HEADPHONES = "\uf025";
    public static final String VOLUME_OFF = "\uf026";
    public static final String VOLUME_DOWN = "\uf027";
    public static final String VOLUME_UP = "\uf028";
    public static final String QRCODE = "\uf029";
    public static final String BARCODE = "\uf02a";
    public static final String TAG = "\uf02b";
    public static final String TAGS = "\uf02c";
    public static final String BOOK = "\uf02d";
    public static final String BOOKMARK = "\uf02e";
    public static final String PRINT = "\uf02f";
    public static final String CAMERA = "\uf030";
    public static final String FONT = "\uf031";
    public static final String BOLD = "\uf032";
    public static final String ITALIC = "\uf033";
    public static final String TEXT_HEIGHT = "\uf034";
    public static final String TEXT_WIDTH = "\uf035";
    public static final String ALIGN_LEFT = "\uf036";
    public static final String ALIGN_CENTER = "\uf037";
    public static final String ALIGN_RIGHT = "\uf038";
    public static final String ALIGN_JUSTIFY = "\uf039";
    public static final String LIST = "\uf03a";
    public static final String INDENT_LEFT = "\uf03b";
    public static final String INDENT_RIGHT = "\uf03c";
    public static final String FACETIME_VIDEO = "\uf03d";
    public static final String PICTURE = "\uf03e";
    public static final String PENCIL = "\uf040";
    public static final String MAP_MARKER = "\uf041";
    public static final String ADJUST = "\uf042";
    public static final String TINT = "\uf043";
    public static final String EDIT = "\uf044";
    public static final String SHARE = "\uf045";
    public static final String CHECK = "\uf046";
    public static final String MOVE = "\uf047";
    public static final String STEP_BACKWARD = "\uf048";
    public static final String FAST_BACKWARD = "\uf049";
    public static final String BACKWARD = "\uf04a";
    public static final String PLAY = "\uf04b";
    public static final String PAUSE = "\uf04c";
    public static final String STOP = "\uf04d";
    public static final String FORWARD = "\uf04e";
    public static final String FAST_FORWARD = "\uf050";
    public static final String STEP_FORWARD = "\uf051";
    public static final String EJECT = "\uf052";
    public static final String CHEVRON_LEFT = "\uf053";
    public static final String CHEVRON_RIGHT = "\uf054";
    public static final String PLUS_SIGN = "\uf055";
    public static final String MINUS_SIGN = "\uf056";
    public static final String REMOVE_SIGN = "\uf057";
    public static final String OK_SIGN = "\uf058";
    public static final String QUESTION_SIGN = "\uf059";
    public static final String INFO_SIGN = "\uf05a";
    public static final String SCREENSHOT = "\uf05b";
    public static final String REMOVE_CIRCLE = "\uf05c";
    public static final String OK_CIRCLE = "\uf05d";
    public static final String BAN_CIRCLE = "\uf05e";
    public static final String ARROW_LEFT = "\uf060";
    public static final String ARROW_RIGHT = "\uf061";
    public static final String ARROW_UP = "\uf062";
    public static final String ARROW_DOWN = "\uf063";
    public static final String SHARE_ALT = "\uf064";
    public static final String RESIZE_FULL = "\uf065";
    public static final String RESIZE_SMALL = "\uf066";
    public static final String PLUS = "\uf067";
    public static final String MINUS = "\uf068";
    public static final String ASTERISK = "\uf069";
    public static final String EXCLAMATION_SIGN = "\uf06a";
    public static final String GIFT = "\uf06b";
    public static final String LEAF = "\uf06c";
    public static final String FIRE = "\uf06d";
    public static final String EYE_OPEN = "\uf06e";
    public static final String EYE_CLOSE = "\uf070";
    public static final String WARNING_SIGN = "\uf071";
    public static final String PLANE = "\uf072";
    public static final String CALENDAR = "\uf073";
    public static final String RANDOM = "\uf074";
    public static final String COMMENT = "\uf075";
    public static final String MAGNET = "\uf076";
    public static final String CHEVRON_UP = "\uf077";
    public static final String CHEVRON_DOWN = "\uf078";
    public static final String RETWEET = "\uf079";
    public static final String SHOPPING_CART = "\uf07a";
    public static final String FOLDER_CLOSE = "\uf07b";
    public static final String FOLDER_OPEN = "\uf07c";
    public static final String RESIZE_VERTICAL = "\uf07d";
    public static final String RESIZE_HORIZONTAL = "\uf07e";
    public static final String BAR_CHART = "\uf080";
    public static final String TWITTER_SIGN = "\uf081";
    public static final String FACEBOOK_SIGN = "\uf082";
    public static final String CAMERA_RETRO = "\uf083";
    public static final String KEY = "\uf084";
    public static final String COGS = "\uf085";
    public static final String COMMENTS = "\uf086";
    public static final String THUMBS_UP = "\uf087";
    public static final String THUMBS_DOWN = "\uf088";
    public static final String STAR_HALF = "\uf089";
    public static final String HEART_EMPTY = "\uf08a";
    public static final String SIGNOUT = "\uf08b";
    public static final String LINKEDIN_SIGN = "\uf08c";
    public static final String PUSHPIN = "\uf08d";
    public static final String EXTERNAL_LINK = "\uf08e";
    public static final String SIGNIN = "\uf090";
    public static final String TROPHY = "\uf091";
    public static final String GITHUB_SIGN = "\uf092";
    public static final String UPLOAD_ALT = "\uf093";
    public static final String LEMON = "\uf094";
    public static final String PHONE = "\uf095";
    public static final String CHECK_EMPTY = "\uf096";
    public static final String BOOKMARK_EMPTY = "\uf097";
    public static final String PHONE_SIGN = "\uf098";
    public static final String TWITTER = "\uf099";
    public static final String FACEBOOK = "\uf09a";
    public static final String GITHUB = "\uf09b";
    public static final String UNLOCK = "\uf09c";
    public static final String CREDIT_CARD = "\uf09d";
    public static final String RSS = "\uf09e";
    public static final String HDD = "\uf0a0";
    public static final String BULLHORN = "\uf0a1";
    public static final String BELL = "\uf0a2";
    public static final String CERTIFICATE = "\uf0a3";
    public static final String HAND_RIGHT = "\uf0a4";
    public static final String HAND_LEFT = "\uf0a5";
    public static final String HAND_UP = "\uf0a6";
    public static final String HAND_DOWN = "\uf0a7";
    public static final String CIRCLE_ARROW_LEFT = "\uf0a8";
    public static final String CIRCLE_ARROW_RIGHT = "\uf0a9";
    public static final String CIRCLE_ARROW_UP = "\uf0aa";
    public static final String CIRCLE_ARROW_DOWN = "\uf0ab";
    public static final String GLOBE = "\uf0ac";
    public static final String WRENCH = "\uf0ad";
    public static final String TASKS = "\uf0ae";
    public static final String FILTER = "\uf0b0";
    public static final String BRIEFCASE = "\uf0b1";
    public static final String FULLSCREEN = "\uf0b2";
    public static final String GROUP = "\uf0c0";
    public static final String LINK = "\uf0c1";
    public static final String CLOUD = "\uf0c2";
    public static final String BEAKER = "\uf0c3";
    public static final String CUT = "\uf0c4";
    public static final String COPY = "\uf0c5";
    public static final String PAPER_CLIP = "\uf0c6";
    public static final String SAVE = "\uf0c7";
    public static final String SIGN_BLANK = "\uf0c8";
    public static final String REORDER = "\uf0c9";
    public static final String LIST_UL = "\uf0ca";
    public static final String LIST_OL = "\uf0cb";
    public static final String STRIKETHROUGH = "\uf0cc";
    public static final String UNDERLINE = "\uf0cd";
    public static final String TABLE = "\uf0ce";
    public static final String MAGIC = "\uf0d0";
    public static final String TRUCK = "\uf0d1";
    public static final String PINTEREST = "\uf0d2";
    public static final String PINTEREST_SIGN = "\uf0d3";
    public static final String GOOGLE_PLUS_SIGN = "\uf0d4";
    public static final String GOOGLE_PLUS = "\uf0d5";
    public static final String MONEY = "\uf0d6";
    public static final String CARET_DOWN = "\uf0d7";
    public static final String CARET_UP = "\uf0d8";
    public static final String CARET_LEFT = "\uf0d9";
    public static final String CARET_RIGHT = "\uf0da";
    public static final String COLUMNS = "\uf0db";
    public static final String SORT = "\uf0dc";
    public static final String SORT_DOWN = "\uf0dd";
    public static final String SORT_UP = "\uf0de";
    public static final String ENVELOPE_ALT = "\uf0e0";
    public static final String LINKEDIN = "\uf0e1";
    public static final String UNDO = "\uf0e2";
    public static final String LEGAL = "\uf0e3";
    public static final String DASHBOARD = "\uf0e4";
    public static final String COMMENT_ALT = "\uf0e5";
    public static final String COMMENTS_ALT = "\uf0e6";
    public static final String BOLT = "\uf0e7";
    public static final String SITEMAP = "\uf0e8";
    public static final String UMBRELLA = "\uf0e9";
    public static final String PASTE = "\uf0ea";
    public static final String LIGHTBULB = "\uf0eb";
    public static final String EXCHANGE = "\uf0ec";
    public static final String CLOUD_DOWNLOAD = "\uf0ed";
    public static final String CLOUD_UPLOAD = "\uf0ee";
    public static final String USER_MD = "\uf0f0";
    public static final String STETHOSCOPE = "\uf0f1";
    public static final String SUITCASE = "\uf0f2";
    public static final String BELL_ALT = "\uf0f3";
    public static final String COFFEE = "\uf0f4";
    public static final String FOOD = "\uf0f5";
    public static final String FILE_ALT = "\uf0f6";
    public static final String BUILDING = "\uf0f7";
    public static final String HOSPITAL = "\uf0f8";
    public static final String AMBULANCE = "\uf0f9";
    public static final String MEDKIT = "\uf0fa";
    public static final String FIGHTER_JET = "\uf0fb";
    public static final String BEER = "\uf0fc";
    public static final String H_SIGN = "\uf0fd";
    public static final String PLUS_SIGN_ALT = "\uf0fe";
    public static final String DOUBLE_ANGLE_LEFT = "\uf100";
    public static final String DOUBLE_ANGLE_RIGHT = "\uf101";
    public static final String DOUBLE_ANGLE_UP = "\uf102";
    public static final String DOUBLE_ANGLE_DOWN = "\uf103";
    public static final String ANGLE_LEFT = "\uf104";
    public static final String ANGLE_RIGHT = "\uf105";
    public static final String ANGLE_UP = "\uf106";
    public static final String ANGLE_DOWN = "\uf107";
    public static final String DESKTOP = "\uf108";
    public static final String LAPTOP = "\uf109";
    public static final String TABLET = "\uf10a";
    public static final String MOBILE_PHONE = "\uf10b";
    public static final String CIRCLE_BLANK = "\uf10c";
    public static final String QUOTE_LEFT = "\uf10d";
    public static final String QUOTE_RIGHT = "\uf10e";
    public static final String SPINNER = "\uf110";
    public static final String CIRCLE = "\uf111";
    public static final String REPLY = "\uf112";
    public static final String GITHUB_ALT = "\uf113";
    public static final String FOLDER_CLOSE_ALT = "\uf114";
    public static final String FOLDER_OPEN_ALT = "\uf115";

    public static final String FILE_EXCEL = "\uf1c3";
    public static final String FILE_PDF = "\uf1c1";
    public static final String FILE_TEXT = "\uf0f6";

    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    private static Font awesomeFont = null;

    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------
    /**
     * Initializes awesome fonts
     *
     * @param awesomeFontURL the url to "fontawesome-webfont.ttf" font
     */
    public static void init(URL awesomeFontURL)
    {
        if (awesomeFontURL == null)
        {
            throw new IllegalArgumentException(
                    "Awesome font url cannot be null!");
        }
        awesomeFont = Font.loadFont(awesomeFontURL.toExternalForm(), 12);
        Log.info("Icon initialized");
    }

    /**
     * Returns true if the font has been initialized
     *
     * @return true: icons can be used
     */
    public static boolean isInitialized()
    {
        return awesomeFont != null;
    }

    /**
     * Sets the font, if initialized, of the labeled item
     *
     * @param labeled the GUI item
     */
    public static void setFont(Labeled labeled)
    {
        if (isInitialized())
        {
            labeled.setFont(awesomeFont);
        }
    }

    /**
     * Sets an icon as the content of a labeled item (e.g. Button or label)
     *
     * @param labeled the GUI item
     * @param icon the icon string
     */
    public static void setIcon(Labeled labeled, String icon)
    {
        labeled.setText(icon);
        setFont(labeled);
    }

    /**
     * Sets an icon as the content of a labeled item (e.g. Button or label)
     *
     * @param labeled the GUI item
     * @param icon the icon string
     * @param alternative if the the class is not initialized the alternative
     * string is used
     */
    public static void setIcon(Labeled labeled, String icon, String alternative)
    {
        if (isInitialized())
        {
            labeled.setText(icon);
            setFont(labeled);
        }
        else
        {
            labeled.setText(alternative);
        }
    }

    /**
     * Creates a button with an icon
     *
     * @param icon the icon
     * @return the button
     */
    public static Button createIconButton(String icon)
    {
        Button b = new Button();
        setIcon(b, icon);
        return b;
    }

    /**
     * Creates a button with an icon
     *
     * @param icon the icon
     * @param alternative if the the class is not initialized the alternative
     * string is used
     * @return the button
     */
    public static Button createIconButton(String icon, String alternative)
    {
        Button b = new Button();
        setIcon(b, icon, alternative);
        return b;
    }

    /**
     * Creates a button with an icon and a tooltip
     *
     * @param icon the icon
     * @param tooltip the tooltip
     * @return the button
     */
    public static Button createIconTooltipButton(String icon,
            String tooltip)
    {
        Button b = createIconButton(icon);
        b.setTooltip(new Tooltip(tooltip));
        return b;
    }

    /**
     * Creates a button with an icon and a tooltip
     *
     * @param icon the icon
     * @param tooltip the tooltip
     * @param alternative if the the class is not initialized the alternative
     * string is used
     * @return the button
     */
    public static Button createIconTooltipButton(String icon,
            String tooltip, String alternative)
    {
        Button b = createIconButton(icon, alternative);
        b.setTooltip(new Tooltip(tooltip));
        return b;
    }

    /**
     * Returns the awesome font
     *
     * @return Awesome font
     */
    public static Font getFont()
    {
        return awesomeFont;
    }

}
