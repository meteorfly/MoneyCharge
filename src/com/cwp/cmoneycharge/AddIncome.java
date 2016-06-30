package com.cwp.cmoneycharge;

import java.util.Calendar;
import java.util.List;
 
import com.cwp.cmoneycharge.R; 
import cwp.moneycharge.dao.IncomeDAO; 
import cwp.moneycharge.dao.ItypeDAO; 
import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.Tb_income;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
public class AddIncome extends Activity{
	protected static final int DATE_DIALOG_ID = 0;// �������ڶԻ�����
	EditText txtInMoney, txtInTime, txtInhandler, txtInMark;// ����4��EditText����
	Spinner spInType;// ����Spinner����
	Button btnInSaveButton;// ����Button���󡰱��桱
	Button btnInCancelButton;// ����Button����ȡ����
	int userid;
	
	private int mYear;// ��
	private int mMonth;// ��
	private int mDay;// ��
    private ArrayAdapter<String> adapter;
    private String[] spdata;
    
	public AddIncome()  {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addincome);// ���ò����ļ�
		
		ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
		txtInMoney = (EditText) findViewById(R.id.txtInMoney);// ��ȡ����ı���
		txtInTime = (EditText) findViewById(R.id.txtInTime);// ��ȡʱ���ı���
		txtInhandler = (EditText) findViewById(R.id.txtInhandler);// ��ȡ����ı���
		txtInMark = (EditText) findViewById(R.id.txtInMark);// ��ȡ��ע�ı���
		spInType = (Spinner) findViewById(R.id.spInType);// ��ȡ��������б�
		btnInSaveButton = (Button) findViewById(R.id.btnInSave);// ��ȡ���水ť
		btnInCancelButton = (Button) findViewById(R.id.btnInCancel);// ��ȡȡ����ť
		
		final Calendar c = Calendar.getInstance();// ��ȡ��ǰϵͳ����
		mYear = c.get(Calendar.YEAR);// ��ȡ���
		mMonth = c.get(Calendar.MONTH);// ��ȡ�·�
		mDay = c.get(Calendar.DAY_OF_MONTH);// ��ȡ����
 
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();// ʵ�ֻ����еķ���
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001);
		updateDisplay();// ��ʾ��ǰϵͳʱ��
		ItypeDAO itypeDAO = new ItypeDAO(AddIncome.this);
		List<String> spdatalist;
		spdatalist=itypeDAO.getItypeName(userid);
		spdata=spdatalist.toArray(new String[spdatalist.size()]);//��tb_itype�а��û�id��ȡ 
		adapter =new ArrayAdapter<String>(AddIncome.this,android.R.layout.simple_spinner_item,spdata); //��̬�������������б�
		spInType.setAdapter(adapter);
		
		
		txtInTime.setOnClickListener(new OnClickListener() {// Ϊʱ���ı������õ��������¼�
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);// ��ʾ����ѡ��Ի���
			}
		});

		btnInSaveButton.setOnClickListener(new OnClickListener() {// Ϊ���水ť���ü����¼�
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String strInMoney = txtInMoney.getText().toString();// ��ȡ����ı����ֵ
				if (!strInMoney.isEmpty()) {// �жϽ�Ϊ��
					// ����InaccountDAO����
					IncomeDAO incomeDAO = new IncomeDAO(
							AddIncome.this); 
					// ����Tb_inaccount����
					 Tb_income tb_income = new Tb_income( 
							userid, 
							incomeDAO.getMaxNo(userid)+1,
							Double.parseDouble(strInMoney), 
							setTimeFormat(), 
							(spInType.getSelectedItemPosition()+1),
							txtInhandler.getText().toString(),
							txtInMark.getText().toString());  
					 incomeDAO.add(tb_income);// ���������Ϣ
					// ������Ϣ��ʾ
					Toast.makeText(AddIncome.this, "���������롽������ӳɹ���",
							Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(AddIncome.this,Income.class);
					intent.putExtra("cwp.id", userid);
					startActivity(intent);
				} else {
					Toast.makeText(AddIncome.this, "�����������",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		btnInCancelButton.setOnClickListener(new OnClickListener() {// Ϊʱ���ı������õ��������¼�
					 @Override
					public void onClick(View arg0) {
						txtInMoney.setText("");// ���ý���ı���Ϊ��
						txtInMoney.setHint("0.00");// Ϊ����ı���������ʾ
						txtInTime.setText("");// ����ʱ���ı���Ϊ�� 
						txtInhandler.setText("");// ���õ�ַ�ı���Ϊ��
						txtInMark.setText("");// ���ñ�ע�ı���Ϊ��
						spInType.setSelection(0);// ������������б�Ĭ��ѡ���һ��
						Intent intent=new Intent(AddIncome.this,Income.class);
						intent.putExtra("cwp.id",userid);
						startActivity(intent);
					}
		}); 
		
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id)// ��дonCreateDialog����
	{
		switch (id) {
		case DATE_DIALOG_ID:// ��������ѡ��Ի���
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;// Ϊ��ݸ�ֵ
			mMonth = monthOfYear;// Ϊ�·ݸ�ֵ
			mDay = dayOfMonth;// Ϊ�츳ֵ
			updateDisplay();// ��ʾ���õ�����
		}
	};

	private void updateDisplay() {
		// ��ʾ���õ�ʱ��
		txtInTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
	} 
	
	private String setTimeFormat(){
		String date=txtInTime.getText().toString();

		int y,m,d;
		String sm,sd;
		int i=0,j=0,k=0; 
		
		for (i = 0; i < date.length(); i++)   
		  {   
		   if (date.substring(i, i + 1).equals("-") && j==0)   
			    j=i;
		   else if(date.substring(i, i + 1).equals("-"))
			    k=i;
		  } 
		y=Integer.valueOf(date.substring(0,j));
		m=Integer.valueOf(date.substring(j+1,k));
		d=Integer.valueOf(date.substring(k+1));
		if(m<10){
			sm="0"+String.valueOf(m);
		}
		else
			sm=String.valueOf(m);
		if(d<10){
			sd="0"+String.valueOf(d);
		}
		else
			sd=String.valueOf(d);
 
		return String.valueOf(y)+"-"+sm+"-"+sd;
		
	}
}

