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
public class AddNote extends Activity{
	EditText txtNote;// ����EditText�������
	Button btnNoteSaveButton;// ����Button�������
	Button btnNoteCancelButton;// ����Button�������
	int userid;
	
	public AddNote() {
		// TODO Auto-generated constructor stub
	}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.addnote);

			ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
			txtNote = (EditText) findViewById(R.id.txtNote);// ��ȡ��ǩ�ı���
			btnNoteSaveButton = (Button) findViewById(R.id.btnNoteSave);// ��ȡ���水ť
			btnNoteCancelButton = (Button) findViewById(R.id.btnNoteCancel);// ��ȡȡ����ť
		
		}
		@Override
		protected void onStart(){
			super.onStart();
			Intent intentr=getIntent();
			userid=intentr.getIntExtra("cwp.id", 100000001);//Ĭ���û�
			btnNoteSaveButton.setOnClickListener(new OnClickListener() {// Ϊ���水ť���ü����¼�
				@Override
					 public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String strNote = txtNote.getText().toString();// ��ȡ��ǩ�ı����ֵ
						if (!strNote.isEmpty()) {// �жϻ�ȡ��ֵ��Ϊ��
							NoteDAO noteDAO = new NoteDAO(AddNote.this);// ����NoteDAP����
							Tb_note tb_note = new Tb_note(userid, noteDAO.getMaxNo(userid) + 1, strNote);// ����Tb_note����
							noteDAO.add(tb_note);// ��ӱ�ǩ��Ϣ
							// ������Ϣ��ʾ
							Toast.makeText(AddNote.this, "��������ǩ��������ӳɹ���",
									Toast.LENGTH_SHORT).show();
								Intent intent=new Intent(AddNote.this,Note.class);
								intent.putExtra("cwp.id",userid);
								startActivity(intent);
						} else {
							Toast.makeText(AddNote.this, "�������ǩ��",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

				btnNoteCancelButton.setOnClickListener(new OnClickListener() {// Ϊȡ����ť���ü����¼�
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								txtNote.setText("");// ��ձ�ǩ�ı���
								Intent intent=new Intent(AddNote.this,Note.class);
								intent.putExtra("cwp.id",userid);
								startActivity(intent);
							}
						});
			
		}

}
