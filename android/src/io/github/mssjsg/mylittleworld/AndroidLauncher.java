package io.github.mssjsg.mylittleworld;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import io.github.mssjsg.mylittleworld.MainGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new ApplicationController(), config);
	}

	@Override
	public void onBackPressed() {
		//TODO handle back press
		super.onBackPressed();
	}
}
