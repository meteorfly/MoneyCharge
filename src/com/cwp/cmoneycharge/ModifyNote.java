package com.cwp.cmoneycharge;

import cwp.moneycharge.dao.NoteDAO;
import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.Tb_note;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ModifyNote extends Activity{
	EditText txtNote;// ����EditText�������
	Button btnnoteManageDelete,btnnoteManageEdit;
	int userid,strno;
	public ModifyNote() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifynote);

		ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
		txtNote = (EditText) findViewById(R.id.txtNote);// ��ȡ��ǩ�ı���
		btnnoteManageDelete = (Button) findViewById(R.id.btnnoteManageDelete);// ��ȡ���水ť
		btnnoteManageEdit = (Button) findViewById(R.id.btnnoteManageEdit);// ��ȡȡ����ť
	
	}
	@Override
	protected void onStart(){
		super.onStart();
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id", 100000001);//Ĭ���û� 
		strno=intentr.getIntExtra("strno",1);
		NoteDAO noteDAO=new NoteDAO(ModifyNote.this);
		Tb_note tb_note=noteDAO.find(userid, strno);
		txtNote.setText(tb_note.getNote().toString());
		btnnoteManageEdit.setOnClickListener(new OnClickListener() {// Ϊ���水ť���ü����¼�
			@Override
				 public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String strNote = txtNote.getText().toString();// ��ȡ��ǩ�ı����ֵ
					if (!strNote.isEmpty()) {// �жϻ�ȡ��ֵ��Ϊ��
						NoteDAO noteDAO = new NoteDAO(ModifyNote.this);// ����NoteDAP����
						Tb_note tb_note = new Tb_note(userid,strno, strNote);// ����Tb_note����
						noteDAO.update(tb_note);// ��ӱ�ǩ��Ϣ
						// ������Ϣ��ʾ
						Toast.makeText(ModifyNote.this, "����ǩ��Ϣ�������޸ĳɹ���",
								Toast.LENGTH_SHORT).show();
							Intent intent=new Intent(ModifyNote.this,Note.class);
							intent.putExtra("cwp.id",userid);
							startActivity(intent);
					} else {
						Toast.makeText(ModifyNote.this, "�������ǩ��",
								Toast.LENGTH_SHORT).show();
					}
				}
			});

			btnnoteManageDelete.setOnClickListener(new OnClickListener() {// Ϊȡ����ť���ü����¼�
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							txtNote.setText("");// ��ձ�ǩ�ı���
							NoteDAO noteDAO=new NoteDAO(ModifyNote.this);
							noteDAO.detele(userid,strno);
							Intent intent=new Intent(ModifyNote.this,Note.class);
							intent.putExtra("cwp.id",userid);
							startActivity(intent);
						}
					});
		
	}

}
