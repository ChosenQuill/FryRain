package com.suredroid.fryrain.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderCallback;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderState;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.suredroid.fryrain.Main;

public class HtmlLauncher extends GwtApplication {

	  @Override
      public GwtApplicationConfiguration getConfig () {
          return new GwtApplicationConfiguration(1600, 900);
      }

      @Override
      public ApplicationListener createApplicationListener () {  
      	return new Main();
      }
  	
      @Override
      public PreloaderCallback getPreloaderCallback() {
      	VerticalPanel container = new VerticalPanel();
  		container.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
  		Image image = new Image();
  		image.setUrl("/assets/images/suredroid.png");
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
    
}