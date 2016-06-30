package com.cwp.cmoneycharge;
 

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cwp.moneycharge.dao.PayDAO; 
import cwp.moneycharge.dao.PtypeDAO;
import cwp.moneycharge.model.Datapicker;
import cwp.moneycharge.model.Tb_pay;

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

public class AddPay extends Activity{
	protected static final int DATE_DIALOG_ID = 0;// �������ڶԻ�����
	EditText txtMoney, txtTime, txtAddress, txtMark;// ����4��EditText����
	Spinner spType;// ����Spinner����
	Button btnSaveButton;// ����Button���󡰱��桱
	Button btnCancelButton;// ����Button����ȡ����
	int userid;
	
	private int mYear;// ��
	private int mMonth;// ��
	private int mDay;// ��

    private ArrayAdapter<String> adapter;
    private String[] spdata;
    
	public AddPay() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpay);// ���ò����ļ�
		
		super.onStart();// ʵ�ֻ����еķ���
		Intent intentr=getIntent(); 

		txtMoney = (EditText) findViewById(R.id.txtMoney);// ��ȡ����ı���
		txtTime = (EditText) findViewById(R.id.txtTime);// ��ȡʱ���ı���
		txtAddress = (EditText) findViewById(R.id.txtAddress);// ��ȡ�ص��ı���
		txtMark = (EditText) findViewById(R.id.txtMark);// ��ȡ��ע�ı���
		spType = (Spinner) findViewById(R.id.spType);// ��ȡ��������б�
		btnSaveButton = (Button) findViewById(R.id.btnSave);// ��ȡ���水ť
		btnCancelButton = (Button) findViewById(R.id.btnCancel);// ��ȡȡ����ť
		
		userid=intentr.getIntExtra("cwp.id",100000001);
		PtypeDAO ptypeDAO = new PtypeDAO(AddPay.this);
		List<String> spdatalist;
		spdatalist=ptypeDAO.getPtypeName(userid);
		spdata=spdatalist.toArray(new String[spdatalist.size()]);//��tb_itype�а��û�id��ȡ 
		adapter =new ArrayAdapter<String>(AddPay.this,android.R.layout.simple_spinner_item,spdata); //��̬�������������б�
		spType.setAdapter(adapter);
		 	 
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
		updateDisplay();// ��ʾ��ǰϵͳʱ��
		userid=intentr.getIntExtra("cwp.id",100000001);
		PtypeDAO ptypeDAO = new PtypeDAO(AddPay.this);
		List<String> spdatalist;
		spdatalist=ptypeDAO.getPtypeName(userid);
		spdata=spdatalist.toArray(new String[spdatalist.size()]);//��tb_itype�а��û�id��ȡ 
		adapter =new ArrayAdapter<String>(AddPay.this,android.R.layout.simple_spinner_item,spdata); //��̬�������������б�
		spType.setAdapter(adapter);
		
		
		txtTime.setOnClickListener(new OnClickListener() {// Ϊʱ���ı������õ��������¼�
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);// ��ʾ����ѡ��Ի���
			}
		});

		btnSaveButton.setOnClickListener(new OnClickListener() {// Ϊ���水ť���ü����¼�
			@SuppressWarnings("deprecation")
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String strOutMoney = txtMoney.getText().toString();// ��ȡ����ı����ֵ
				if (!strOutMoney.isEmpty()) {// �жϽ�Ϊ��
					// ����InaccountDAO����
					PayDAO payDAO = new PayDAO(
							AddPay.this); 
					// ����Tb_inaccount����
					 Tb_pay tb_pay = new Tb_pay( 
							userid, 
							payDAO.getMaxNo(userid)+1,
							Double.parseDouble(strOutMoney), 
							setTimeFormat(), 
							(spType.getSelectedItemPosition()+1),
							txtAddress.getText().toString(),
							txtMark.getText().toString());  
					payDAO.add(tb_pay);// ���������Ϣ 
					Toast.makeText(AddPay.this, "���������롽������ӳɹ���  \n" ,Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(AddPay.this,Pay.class);
					intent.putExtra("cwp.id", userid);
					startActivity(intent);
				} else {
					Toast.makeText(AddPay.this, "�����������",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		btnCancelButton.setOnClickListener(new OnClickListener() {// Ϊʱ���ı������õ��������¼�
					 @Override
					public void onClick(View arg0) {
						txtMoney.setText("");// ���ý���ı���Ϊ��
						txtMoney.setHint("0.00");// Ϊ����ı���������ʾ
						txtTime.setText("");// ����ʱ���ı���Ϊ�� 
						txtAddress.setText("");// ���õ�ַ�ı���Ϊ��
						txtMark.setText("");// ���ñ�ע�ı���Ϊ��
						spType.setSelection(0);// ������������б�Ĭ��ѡ���һ��
						Intent intent=new Intent(AddPay.this,Pay.class);
						intent.putExtra("cwp.id", userid);
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
		
		
		txtTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth+1).append("-").append(mDay));
		
	}
	private String setTimeFormat(){
		String date=txtTime.getText().toString();

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
 
