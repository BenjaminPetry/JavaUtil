/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.gui;

import de.bpetry.util.Util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

/**
 * Implements a browser with navigation icons based on the javafx webengine and
 * webview
 *
 * @author Benjamin Petry
 */
public class WebBrowser extends VBox
{

    //-------------------------------------------------------------------------
    ////////////////////////////////  Constants ///////////////////////////////
    //-------------------------------------------------------------------------
    final public static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";

    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    private final WebView view;
    private final WebEngine engine;
    private final Button back;
    private final Button forward;
    private final Button refresh;
    private final TextField url;
    private final Label state;
    private final Button go;
    private final Button goExternal;
    private final Button copyUrl;
    private final ProgressIndicator indicator;
    private boolean hideEmptyPage = false;
    private boolean isCancelled = false;

    private final HBox toolbar = new HBox();

    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------
    public WebBrowser()
    {
        this(USER_AGENT);
    }

    public WebBrowser(String agent)
    {
        super(5);
        // TOOLBAR SETUP
        back = Icon.createIconTooltipButton(Icon.BACKWARD, "Go back", "<");
        forward = Icon.createIconTooltipButton(Icon.FORWARD, "Go forward", ">");
        refresh = Icon.createIconTooltipButton(Icon.REFRESH, "Refresh page", "@");
        go = Icon.createIconTooltipButton(Icon.PLAY, "Go", "Go");
        goExternal = Icon.createIconTooltipButton(Icon.EXTERNAL_LINK,
                "Open in external browser", "->");
        copyUrl = Icon.createIconTooltipButton(Icon.COPY,
                "Copy URL to clipboard", "Copy");
        state = new Label();
        indicator = new ProgressIndicator();
        url = new TextField("");
        initToolbar();

        // WEBVIEW AND ENGINE
        view = new WebView();
        engine = view.getEngine();
        engine.setUserAgent(agent);
        engine.getLoadWorker().stateProperty().addListener((
                ObservableValue<? extends Worker.State> observable,
                Worker.State oldValue, Worker.State newValue) ->
        {
            switch (newValue)
            {
                case FAILED:
                    state.setText("Could not load webpage");
                case CANCELLED:
                case READY:
                case SUCCEEDED:
                    checkURL(engine.getLocation());
                    back.setDisable(!isBackPossible());
                    forward.setDisable(!isForwardPossible());
                    refresh.setDisable(isBlankPage());
                    indicator.setVisible(false);
                    Icon.setIcon(go, Icon.PLAY, "Go");
                    break;
                case SCHEDULED:
                    if (isCancelled)
                    {
                        isCancelled = false;
                    }
                    else if (!isBlankPage())
                    {
                        setUrlField(engine.getLocation());
                    }
                case RUNNING:
                    state.setText("");
                    back.setDisable(true);
                    forward.setDisable(true);
                    refresh.setDisable(true);
                    indicator.setVisible(true);
                    Icon.setIcon(go, Icon.STOP, "Cancel");
                    break;
            }
        });
        initPane();
    }

    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------
    /**
     * Returns the current loaded URL of the webpage
     *
     * @return the url
     */
    public String getUrl()
    {
        return this.engine.getLocation();
    }

    /**
     * Returns the text in the url textfield
     *
     * @return url of the textfield
     */
    public String getUrlField()
    {
        return this.url.getText();
    }

    /**
     * Sets the text in the url field
     *
     * @param url field
     */
    public void setUrlField(String url)
    {
        this.url.setText(url);
        checkURL(url);
    }

    /**
     * Sets whether the webview should be hidden when an empty page is loaded
     *
     * @param hide true to hide the webview on an empty page
     */
    public void hideOnEmptyPage(boolean hide)
    {
        hideEmptyPage = hide;
    }

    /**
     * Loads a url
     *
     * @param url the url to load
     */
    public void load(String url)
    {
        String formatedUrl = formatUrlInput(url);
        this.url.setText(formatedUrl);
        this.view.setVisible(!this.hideEmptyPage || !formatedUrl.isEmpty());
        engine.load(formatedUrl);
    }

    /**
     * Cancels the current loading
     */
    public void cancel()
    {
        isCancelled = true;
        engine.load(null);
    }

    /**
     * Reloads the current webpage
     */
    public void reload()
    {
        if (!isBlankPage())
        {
            engine.reload();
        }
    }

    /**
     * Goes one back in the browsing history
     *
     * @return true if going back was possible
     */
    public boolean back()
    {
        if (isBackPossible())
        {
            engine.getHistory().go(-1);
            return true;
        }
        return false;
    }

    /**
     * Goes one forward in the browsing history
     *
     * @return true if going forward was possible
     */
    public boolean forward()
    {
        if (isForwardPossible())
        {
            engine.getHistory().go(1);
            return true;
        }
        return false;
    }

    /**
     * Determines whether the current loaded URL is a blank page
     *
     * @return true if the page is blank
     */
    public boolean isBlankPage()
    {
        String currentUrl = getUrl();
        return currentUrl == null || currentUrl.equals("") || currentUrl.equals(
                "about:blank");
    }

    /**
     * Determines whether the browsing history has past elements
     *
     * @return true if there are past elements
     */
    public boolean isBackPossible()
    {
        WebHistory history = engine.getHistory();
        return history.getCurrentIndex() > 0;
    }

    /**
     * Determines whether the browsing history has next elements
     *
     * @return true if there are next elements
     */
    public boolean isForwardPossible()
    {
        WebHistory history = engine.getHistory();
        return history.getCurrentIndex() < history.getEntries().size() - 1;
    }

    /**
     * Returns the webview
     *
     * @return view
     */
    public WebView getView()
    {
        return view;
    }

    /**
     * Returns the web engine
     *
     * @return engine
     */
    public WebEngine getEngine()
    {
        return engine;
    }

    /**
     * Returns the main toolbar
     *
     * @return toolbar
     */
    public HBox getMainToolBar()
    {
        return toolbar;
    }

    /**
     * Add a toolbar to the view
     *
     * @param toolbar the toolbar
     * @param index its position
     */
    public void addToolbar(Node toolbar, int index)
    {
        int indexAdjusted = Math.max(0, Math.min(index,
                this.getChildren().size() - 1));
        this.getChildren().add(indexAdjusted, toolbar);
    }

    /**
     * Add a toolbar to the view at the end of toolbars
     *
     * @param toolbar the toolbar
     */
    public void addToolbar(Node toolbar)
    {
        addToolbar(toolbar, this.getChildren().size()); // will be corrected automatically
    }

    /**
     * Returns the toolbar at a specific position
     *
     * @param index position
     * @return toolbar
     */
    public Node getToolbar(int index)
    {
        if (index > this.getChildren().size() - 2)
        {
            throw new IllegalArgumentException("No Toolbar on position " + index);
        }
        return this.getChildren().get(index);
    }

    /**
     * Removes a toolbar from the browser
     *
     * @param toolbar toolbar to remove
     */
    public void removeToolbar(Node toolbar)
    {
        this.getChildren().remove(toolbar);
    }

    /**
     * Removes a toolbar from the browser
     *
     * @param index position of the toolbar to remove
     */
    public void removeToolbar(int index)
    {
        if (index > this.getChildren().size() - 2)
        {
            throw new IllegalArgumentException("No Toolbar on position " + index);
        }
        this.getChildren().remove(index);
    }

    //-------------------------------------------------------------------------
    /////////////////////////////  Private Methods ////////////////////////////
    //-------------------------------------------------------------------------
    private void initToolbar()
    {
        toolbar.setPadding(new Insets(5, 5, 0, 5));
        url.setPrefWidth(150);
        HBox.setHgrow(url, Priority.SOMETIMES);
        indicator.setPrefSize(20, 20);
        indicator.setMinSize(20, 20);
        indicator.setVisible(false);
        back.setDisable(true);
        forward.setDisable(true);
        refresh.setDisable(true);
        toolbar.setSpacing(10);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.getChildren().add(back);
        toolbar.getChildren().add(forward);
        toolbar.getChildren().add(refresh);
        toolbar.getChildren().add(new Separator(Orientation.VERTICAL));
        toolbar.getChildren().add(url);
        toolbar.getChildren().add(go);
        toolbar.getChildren().add(goExternal);
        toolbar.getChildren().add(copyUrl);
        toolbar.getChildren().add(indicator);
        toolbar.getChildren().add(state);

        // ACTIONS
        copyUrl.setOnAction((event) ->
        {
            if (url.getText() != null && !url.getText().isEmpty())
            {
                Util.copyToClipboard(url.getText());
            }
        });
        goExternal.setOnAction((event) ->
        {
            String formatedUrl = formatUrlInput(url.getText());
            if (!formatedUrl.isEmpty())
            {
                Util.openUrl(formatedUrl);
            }
        });
        go.setOnAction((event) ->
        {
            if (engine.getLoadWorker().isRunning())
            {
                cancel();
            }
            else
            {
                load(url.getText());
            }
        });
        url.setOnAction((event) ->
        {
            this.load(url.getText());
        });
        back.setOnAction((event) ->
        {
            back();
        });
        refresh.setOnAction((event) ->
        {
            reload();
        });
        forward.setOnAction((event) ->
        {
            forward();
        });
    }

    private void initPane()
    {
        this.getChildren().add(toolbar);
        this.getChildren().add(view);
    }

    private void checkURL(String url)
    {
        URLType type = URLType.getType(url);
        if (type != URLType.URL)
        {
            this.state.setText("Warning: Cannot open " + type.toString());
        }
    }

    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    /**
     * This method takes an URL or text in general as input and generates a
     * usable URL. E.g. if http:// is forgotten it adds it. If the input are
     * search terms it will create a google search link
     *
     * @param url the text to transform to a URL
     * @return a URL
     */
    public static String formatUrlInput(String url)
    {
        if (url == null)
        {
            url = "";
        }
        String tmp = url.replace(":\\\\", "://");
        if (!isValidURL(url) && !tmp.isEmpty())
        {
            try
            {
                String searchTerm = URLEncoder.encode(tmp, "UTF-8");
                tmp = "https://www.google.com/search?q=" + searchTerm;
            }
            catch (UnsupportedEncodingException ex)
            {
                tmp = "https://www.google.com";
            }
        }
        else if (!tmp.matches(".*(://).*") && !tmp.isEmpty())
        {
            tmp = "http://" + tmp;
        }

        return tmp;
    }

    public static boolean isValidURL(String url)
    {
        return isIP(url) || isWebpage(url);
    }

    public static boolean isIP(String s)
    {
        return s.matches(
                "(.*(://))?(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}).*");
    }

    public static boolean isWebpage(String s)
    {
        return s.matches("(.*(://).*)?\\w+?\\.\\w\\w.*");
    }

}
