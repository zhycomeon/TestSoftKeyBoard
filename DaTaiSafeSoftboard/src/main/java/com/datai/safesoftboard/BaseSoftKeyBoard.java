package com.datai.safesoftboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.datai.safesoftboard.interfaces.SoftKeyStatusListener;


/**
 * 自定义软件键盘
 */
public class BaseSoftKeyBoard extends PopupWindow implements
		SoftKeyStatusListener,OnClickListener{

	private static final String TAG=BaseSoftKeyBoard.class.getSimpleName();
	protected View contentView;
	protected View decorView;
	protected SafeEdit edit;
	protected ImageView switchType;
	protected ImageView colseSoftKey;
	protected TextView softKeyBoard_tip;

	protected int viewMode;
	protected boolean isPendding=true;
	protected int softKeyHeight;
	protected int decorViewHeight;
	protected StringBuilder inputStr;

	//
	protected String desString = null;

	protected Context mContext = null;

	public BaseSoftKeyBoard(Context context) {
		this(context,null);
	}

	public BaseSoftKeyBoard(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	@SuppressWarnings("deprecation")
	public BaseSoftKeyBoard(Context context, AttributeSet attrs,
							int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;

		decorView=((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

		setFocusable(true);
		setOutsideTouchable(false);

        setBackgroundDrawable(new BitmapDrawable());
        setWidth(decorView.getWidth());
		setAnimationStyle(android.R.style.Animation_InputMethod);
		//contentView.getViewTreeObserver().addOnGlobalLayoutListener(contetviewLayoutListener);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id == R.id.softkeyBoard_colse){
			close();
		}else if(id == R.id.softkeyBoard_switchType){
//			openDefaulSoftKeyboard();
			if(edit != null){
				edit.setSystemInputSwitch(true);
			}
			close();
		}
//		switch (v.getId()) {
//		case R.id.softkeyBoard_colse:
//			close();
//			break;
//		case R.id.softkeyBoard_switchType:
////			if(inputTypeGroup.getVisibility()==View.VISIBLE){
////				softKeyBoard_tip.setVisibility(View.VISIBLE);
////				inputTypeGroup.setVisibility(View.GONE);
////			}else{
////				softKeyBoard_tip.setVisibility(View.GONE);
////				inputTypeGroup.setVisibility(View.VISIBLE);
////			}
//
//			break;
//		}
	}


	/**
	 * 显示输入法
	 */
	public void show(){
		if(edit==null){
			new IllegalAccessError("safeEdit is null refrence");
		}
		inputStr=new StringBuilder(edit.getText().toString());
		showAsDropDown(decorView, 0, -getHeight());

		//updateViewByMode(getInputType(edit));
	}


	/**
	 * 释放相关的资源
	 */
	public void recycle(){
		edit=null;
		decorView=null;
	}

	/**
	 * 关闭输入法
	 */
	public void close(){
		if(isShowing()){
			recycle();
			dismiss();
		}
	}

	/**
	 * 重新刷新页面排版
	 */
	public void updateViewDraw(){
		if(isPendding){
			
		}
	}
	
	public OnGlobalLayoutListener contetviewLayoutListener=new OnGlobalLayoutListener(){

		@Override
		public void onGlobalLayout() {
			// TODO Auto-generated method stub
			if(decorViewHeight==decorView.getHeight()){
				Log.i(TAG, "updateViewDraw-->decorViewHeight="+decorViewHeight);
				TranslateAnimation translate=new TranslateAnimation(0, 0, 0, softKeyHeight);
				translate.setDuration(200);
				translate.setFillAfter(true);
				decorView.startAnimation(translate);
			}else{
				Log.i(TAG, "updateViewDraw-->decorViewHeight="+decorViewHeight);
				TranslateAnimation translate=new TranslateAnimation(0, 0, -softKeyHeight, 0);
				translate.setDuration(200);
				translate.setFillAfter(true);
				decorView.startAnimation(translate);
			}
		}

	};
	
	@Override
	public void onPressed(SoftKey softKey) {
//		if(edit!=null){
//			inputStr.append(softKey.getText());
//			String oriStr = inputStr.toString();
//			edit.setText(oriStr);
//			edit.setSelection(inputStr.length());
//
//		}
		if(edit != null) {
			edit.processKeyValue(BusinessType.BUSINESS_TYPE6, softKey.getText());
		}
	}

	@Override
	public void onDeleted() {
//		if(edit!=null&&(!TextUtils.isEmpty(inputStr.toString()))){
//			edit.setText(inputStr.deleteCharAt(inputStr.length()-1));
//			edit.setSelection(inputStr.length());
//		}

		if (edit != null) {
			edit.processKeyValue(BusinessType.BUSINESS_TYPE9, "");
		}
	}

    @Override
    public void onConfirm() {
        close();
    }

    @Override
    public void onJMEAdd() {

    }

    @Override
    public void onJMEMin() {
    }

	@Override
	public void onAllPosition() {

	}

	@Override
	public void onHalfPosition() {

	}

	@Override
	public void onOneThirdsPosition() {

	}

	@Override
	public void onTwoThirdsPosition() {

	}

	@Override
	public void onOneFourthsPosition() {

	}

	public void setEdit(SafeEdit edit) {
        this.edit = edit;
    }

	public boolean isPendding() {
		return isPendding;
	}

	public void setPendding(boolean isPendding) {
		this.isPendding = isPendding;
	}

	public int  getSoftKeyboardHeight(){
		return softKeyHeight;
	}

	public int getDecorViewHeight(){
		return decorViewHeight;
	}

	public void openDefaulSoftKeyboard(){
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(edit, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
//		if(edit != null){
//			edit.setText("");
//		}
	}

	public void setBusinessTips(String businessStr) {

	}
}
