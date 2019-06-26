package com.dealimg.leo.androidpicdeal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private ImageView mChangeColorIv;
    private Bitmap mBitmap;
    private float mHue = 0, mSaturation = 1f, mLum = 1f;
    private static final int MID_VALUE = 128;
    private Bitmap changedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChangeColorIv =  findViewById(R.id.change_color_iv);
        ImageView imgOld = findViewById(R.id.img_old);
        SeekBar mHueSeekBar = findViewById(R.id.hue_seek_bar);
        SeekBar mSaturationSeekBar = findViewById(R.id.saturation_seek_bar);
        SeekBar mLumSeekBar = findViewById(R.id.lum_seek_bar);
        //获得图片资源
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        imgOld.setImageBitmap(mBitmap);
        mChangeColorIv.setImageBitmap(mBitmap);
        //对seekBar设置监听
        mHueSeekBar.setOnSeekBarChangeListener(this);
        mSaturationSeekBar.setOnSeekBarChangeListener(this);
        mLumSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.hue_seek_bar: // 调整色相
                //色相的范围是正负180
                mHue = (progress - MID_VALUE) * 1f / MID_VALUE * 180;
                break;
            case R.id.saturation_seek_bar: // 调整饱和度
                //范围是0-2;
                mSaturation = progress * 1f / MID_VALUE;
                break;
            case R.id.lum_seek_bar: // 调整亮度
                mLum = progress * 1f / MID_VALUE;
                break;
        }
        changedBitmap = ImageHelper.getChangedBitmap(mBitmap, mHue, mSaturation, mLum);
        mChangeColorIv.setImageBitmap(changedBitmap);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void onClick(View v) {
        if (changedBitmap == null) {
            Toast.makeText(this, "请先调整亮度", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = PhotoUtil.sharpenImageAmeliorate(changedBitmap);
        mChangeColorIv.setImageBitmap(bitmap);
    }
}