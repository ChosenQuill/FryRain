package com.suredroid.fryrain.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderCallback;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderState;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.suredroid.fryrain.Main;

public class HtmlLauncher extends GwtApplication {

    private static final String AutoHorizontalMargin = "auto-horizontal-margin";
    private static final String AutoVerticalMargin = "auto-vertical-margin";
    
    private float widthHeightRatio = -1;
    private int minWidth;
    private int minHeight;
    private int prefWidth;
    private int prefHeight;
    
    private int width;
    private int height;
    
    private int 
    //Either these two
    inputWidth, 
    inputHeight, 
    //Or these 4;
    inputMinWidth = 160, 
    inputMinHeight = 90, 
    inputPrefWidth = 1600, 
    inputPrefHeight = 900;
    
    @Override
    public void onModuleLoad() {
    	 if (inputWidth!=0 && inputHeight!=0) {
             minWidth = inputWidth;
             minHeight = inputHeight;
             prefWidth = minWidth;
             prefHeight = minHeight;
             widthHeightRatio = (float) minWidth / minHeight;
         } else {
             minWidth = inputMinWidth;
             minHeight = inputMinHeight;
             if (minWidth > 0 && minHeight > 0) {
                 widthHeightRatio = (float) minWidth / minHeight;
             }
             
             prefWidth = inputPrefWidth;
             prefHeight = inputPrefHeight;
             if (prefWidth == Integer.MAX_VALUE && prefHeight == Integer.MAX_VALUE) {
                 if (minWidth > minHeight) {
                     prefHeight = toHeight(prefWidth);
                 } else {
                     prefWidth = toWidth(prefHeight);
                 }
             } else {
                 widthHeightRatio = (float) prefWidth / prefHeight; 
             }
         }
         super.onModuleLoad();
    }
    
    @Override
    public ApplicationListener createApplicationListener() {
        setLoadingListener(new LoadingListener() {
            @Override
            public void beforeSetup() {
            	
            }
            @Override
            public void afterSetup() {
                setupResizing();
            }
        });
        
       return new Main();
    }

    
    private void setupResizing() {
        Window.setMargin("0");
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                int clientWidth = event.getWidth();
                int clientHeight = event.getHeight()-65;
                calculateSize(clientWidth, clientHeight);
                Window.enableScrolling(width > clientWidth || height > clientHeight);
                getGraphics().setWindowedMode(width, height);
                
                Panel rootPanel = getRootPanel();
                rootPanel.setSize(width + "px", height + "px");
                if (width > clientWidth) {
                    rootPanel.removeStyleName(AutoHorizontalMargin);
                } else {
                    rootPanel.addStyleName(AutoHorizontalMargin);
                }
                if (height > clientHeight) {
                    rootPanel.removeStyleName(AutoVerticalMargin);
                } else {
                    rootPanel.addStyleName(AutoVerticalMargin);
                }
            }
        });
    }
    
    @Override
    public GwtApplicationConfiguration getConfig() {
        int clientWidth = Window.getClientWidth();
        int clientHeight = Window.getClientHeight()-65;
        calculateSize(clientWidth, clientHeight);
        
        Window.enableScrolling(width > clientWidth || height > clientHeight);
        Panel rootPanel = createGamePanel(width, height);
        if (width <= clientWidth) {
            rootPanel.addStyleName(AutoHorizontalMargin);
        }
        if (height <= clientHeight) {
            rootPanel.addStyleName(AutoVerticalMargin);
        }
        
        GwtApplicationConfiguration configuration = new GwtApplicationConfiguration(width, height);
        configuration.preferFlash = false;
        configuration.rootPanel = rootPanel;
        return configuration;
    }

    private void calculateSize(int clientWidth, int clientHeight) {
        if (widthHeightRatio <= 0) {
            width = clientWidth;
            height = clientHeight;
        } else {
            width = prefWidth;
            height = prefHeight;
            if (width > clientWidth) {
                width = Math.max(clientWidth, minWidth);
                height = toHeight(width);
            } 
            if (height > clientHeight) {
                height = Math.max(clientHeight, minHeight);
                width = toWidth(height);
            }
        }
    }

    private Panel createGamePanel(int width, int height) {
        Panel gamePanel = new FlowPanel() {
            @Override
            public void onBrowserEvent(Event event) {
                int eventType = DOM.eventGetType(event);
                if (eventType == Event.ONMOUSEDOWN || eventType == Event.ONMOUSEUP) {
                    event.preventDefault();
                    event.stopPropagation();
                    Element target = event.getEventTarget().cast();
                    target.getStyle().setProperty("cursor", eventType == Event.ONMOUSEDOWN ? "default" : "");
                }
                super.onBrowserEvent(event);
            }
        };
        gamePanel.setWidth(width + "px");
        gamePanel.setHeight(height + "px");
        gamePanel.addStyleName("root");
        RootPanel.get().add(gamePanel);
        return gamePanel;
    }
    
    @Override
    public PreloaderCallback getPreloaderCallback() {
    	VerticalPanel container = new VerticalPanel();
		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Image image = new Image();
		image.setUrl("/suredroid.png");
		container.add(image);
		getRootPanel().add(container);
    	
    	return new PreloaderCallback() {
    		
    		@Override
            public void error(String file) {
                HtmlLauncher.this.error("Preloading error", file);
            }

			@Override
			public void update(PreloaderState state) {
				
			}
			
        };
    }
    
    private int toHeight(int width) {
        if (widthHeightRatio <= 0) {
            return width;
        }
        return Math.round(width / widthHeightRatio);
    }
    
    private int toWidth(int height) {
        if (widthHeightRatio <= 0) {
            return height;
        }
        return Math.round(height * widthHeightRatio);
    }
    
}