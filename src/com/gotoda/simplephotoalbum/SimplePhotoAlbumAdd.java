package com.gotoda.simplephotoalbum;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

public class SimplePhotoAlbumAdd extends Activity {
	
	// ���i�̐錾
	ImageView imageViewAdd;
	Spinner spinnerAdd;
	EditText editTextAdd;
	RatingBar ratingBarAdd;
	
	

    ////////////////////////////////////////////////////////    														
    /**
     * onCreate
     * @param con�@�R�l�N�V�����ł�
     * @param tradebean�@���̓t�H�[���ɓ��͂��ꂽ�����������Ă��܂�
     * @return �Ȃ�
     * @throws SQLException SQL���s���ɋN���肤���O
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onCreate(savedInstanceState);
		// ���C�A�E�g�̃Z�b�g
		setContentView(R.layout.activity_add);
		
		// ���C�A�E�g��̕��i��ϐ��ɑ��
		imageViewAdd 
			= (ImageView)findViewById(R.id.ImageViewAdd);
		spinnerAdd = (Spinner)findViewById(R.id.SpinnerAdd);
		editTextAdd = (EditText)findViewById(R.id.EditTextAdd);
		ratingBarAdd = (RatingBar)findViewById(R.id.RatingBarAdd);
		
		// SD�J�[�h�̉摜�t�@�C���Q���X�s�i�[�ɃZ�b�g
		// adapter�̍쐬
		ArrayAdapter<String> adapter
			= new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item);
		// SD�J�[�h�̃f�B���N�g�����擾
		File directory
			= Environment.getExternalStorageDirectory();
		// SD�J�[�h�̃f�B���N�g�����炷�ׂẴt�@�C�����擾
		File[] files = directory.listFiles();
		// adapter�Ƀt�@�C������ǉ�
		for ( int i=0; i<files.length; i++ ){
			// JPEG�t�@�C���݂̂�ǉ�
			if( files[i].getName().endsWith(".jpg")){
				adapter.add(files[i].getPath());
			}
		}
		// spinnerAdd��adapter���Z�b�g
		spinnerAdd.setAdapter(adapter);
		
		// spinnerFile����I�������v�f�����o��
		spinnerAdd.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO �����������ꂽ���\�b�h�E�X�^�u
						// �I�������v�f�̈ʒu�����O�ɏo��
						Log.d("[Spinner]", "Position; "+ arg2);
						
						// �I�����ꂽ�t�@�C��
						String selectedFile
							= (String)arg0.getItemAtPosition(arg2);
						// �I�����ꂽ�t�@�C������r�b�g�}�b�v�𐶐�
						Bitmap bitmap
							= BitmapFactory.decodeFile(selectedFile);
						// �C���[�W�r���[�Ƀr�b�g�}�b�v���Z�b�g
						imageViewAdd.setImageBitmap(bitmap);
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO �����������ꂽ���\�b�h�E�X�^�u
						
					}
					
				}
		);
	}
	
	
    ////////////////////////////////////////////////////////    														
    /**
     * closeActivity
     * @param con�@�R�l�N�V�����ł�
     * @param tradebean�@���̓t�H�[���ɓ��͂��ꂽ�����������Ă��܂�
     * @return �Ȃ�
     * @throws SQLException SQL���s���ɋN���肤���O
     */
	public void closeActivity(View view){
		// Set�{�^�����N���b�N���ꂽ�Ƃ��ɒl�𑗐M
		if( view.getId() == R.id.ButtonSet){
			// �C���e���g�̃p�����[�^�ɒl���Z�b�g����
			Intent intent = new Intent();
			intent.putExtra("file", spinnerAdd.getSelectedItem().toString());
			intent.putExtra("title", editTextAdd.getText().toString());
			intent.putExtra("rating", ratingBarAdd.getRating());
			
			// ���C����ʂɃp�����[�^�𑗐M����
			setResult(Activity.RESULT_OK, intent);
			
		}
		
		
		// �o�^��ʂ����
		finish();
	}
	
	

}
