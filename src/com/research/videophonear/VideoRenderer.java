package com.research.videophonear;

import rajawali.Object3D;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.VideoTexture;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Plane;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.research.phonearlib.camera.TrackData;
import com.research.phonearlib.renderer.BaseRenderer;

public class VideoRenderer extends BaseRenderer{
	private MediaPlayer mMediaPlayer;
	private VideoTexture mVideoTexture;
	private Object3D mPlane;
	private boolean firstTrack = true;
	
	//private String url = "rtsp://r2---sn-a5m7zu7r.googlevideo.com/Ck0LENy73wIaRAnntm6E46MaYxMYDSANFC0-WNpTMOCoAUIJbXYtZ29vZ2xlSARSBXdhdGNoYM6M4d6V-p6AUooBC1lOSWlLZVZiX3BVDA==/C1BFF400D6ECF59CDA83EA80E908BCCB0F6D105F.6D5D4AF4775DF42D7C72A7B045A9E8E7E12F7DF1/yt5/1/video.3gp";
	
	public VideoRenderer(Context context, TrackData td) {
		super(context, td);
		mMediaPlayer = MediaPlayer.create(getContext(),
				R.raw.test);	
		mMediaPlayer.setLooping(true);
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
 
	}
	
	protected void initScene(){


		//create video texture and set material
		mVideoTexture = new VideoTexture("test", mMediaPlayer);
		Material material = new Material();
		material.setColorInfluence(0);
		try {
			material.addTexture(mVideoTexture);
		} catch (TextureException e) {
			e.printStackTrace();
		}

		//set up the plane
		mPlane = new Plane(3, 2, 1, 1);
		mPlane.setScale(0.5);
		mPlane.setMaterial(material);
		mPlane.rotateAround(new Vector3(0,1,0), 180.0);
		mPlane.setDoubleSided(true);
		addChild(mPlane);
	}

	@Override
	protected void drawModel(boolean tracked) {
		mPlane.setVisible(false);
		mVideoTexture.update();
		
		if(firstTrack){
			mMediaPlayer.start();
			firstTrack = false;
		}
		
		if(tracked){						
			mPlane.setVisible(true);			
		}
		
	}

}
