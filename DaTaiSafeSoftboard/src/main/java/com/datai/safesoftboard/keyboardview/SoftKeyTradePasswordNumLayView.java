package com.datai.safesoftboard.keyboardview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.datai.safesoftboard.R;
import com.datai.safesoftboard.key.SoftKey;


/**
 * 数字键盘布局
 */
public class SoftKeyTradePasswordNumLayView extends SoftKeyView {

    /**
     * 数字键盘的分布4行3列
     */
    private int row = 4;
    private int col = 3;


    public SoftKeyTradePasswordNumLayView(Context context) {
        this(context, null);
    }

    public SoftKeyTradePasswordNumLayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoftKeyTradePasswordNumLayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public SoftKey[] initSoftKeys() {
        SoftKey[] result = new SoftKey[10];
        for (int i = 0; i < result.length; i++) {
            SoftKey btn = new SoftKey();

            btn.setText(String.valueOf(i));

            result[i] = btn;
        }
        return result;
    }

    @Override
    public SoftKey[] measureSoftKeysPos(SoftKey[] softKeys) {
        for (int i = 1; i < softKeys.length; i++) {
            softKeys[i].setX(blockWidth / 2 + ((i - 1) % col) * blockWidth);
            softKeys[i].setY(blockHeight / 2 + ((i - 1) / col % row) * blockHeight);
            softKeys[i].setWidth(blockWidth - 2 * gapWidth);
            softKeys[i].setHeight(blockHeight - 2 * gapHeight);
        }

        softKeys[0].setX(blockWidth / 2 + blockWidth);
        softKeys[0].setY(blockHeight / 2 + 3 * blockHeight);
        softKeys[0].setWidth(blockWidth - 2 * gapWidth);
        softKeys[0].setHeight(blockHeight - 2 * gapHeight);

        return softKeys;
    }

    @Override
    public int measureBlockWidth(int keyBoardwidth) {
        return keyBoardwidth / col;
    }

    @Override
    public int measureBlockHeight(int keyBoardHeight) {
        return keyBoardHeight / row;
    }

    @Override
    public void drawSoftKeysPos(Canvas canvas, SoftKey[] softKeys) {
        if (softKeys == null) {
            return;
        }

        canvas.drawColor(0xffcccccc);

        //画软键盘的10个数字按钮
        for (int index = 0; index < softKeys.length; index++) {
            drawSoftKey(canvas, softKeys[index]);
        }

        //创建软键盘的删除按钮
        if (delBtn == null) {
            delBtn = new SoftKey();
            delBtn.setKeyType(SoftKey.KeyType.ICON);
            delBtn.setIcon(R.drawable.ic_softkey_delete);
            delBtn.setHeight(blockHeight - gapHeight * 2);
            delBtn.setWidth(blockWidth - gapWidth * 2);
            delBtn.setX(blockWidth / 2 + ((col - 1) % col) * blockWidth);
            delBtn.setY(blockHeight / 2 + ((row - 1) % row) * blockHeight);

        }

        //画软键盘的删除按钮
        drawSoftKey(canvas, delBtn);

    }


    @Override
    public boolean handleKeyTouching(int eventX, int eventY, int action) {
        boolean needRefresh = super.handleKeyTouching(eventX, eventY, action);

        return needRefresh;
    }

    @Override
    public boolean handleTouchUp(int eventX, int eventY, int action) {
        return super.handleTouchUp(eventX, eventY, action);
    }

}
