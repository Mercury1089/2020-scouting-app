package com.mercury1089.scoutingapp2019.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mercury1089.scoutingapp2019.HashMapManager;
import com.mercury1089.scoutingapp2019.R;
import com.mercury1089.scoutingapp2019.SettingsActivity;
import static com.mercury1089.scoutingapp2019.utils.GenUtils.padLeftZeros;

public class ListAdapter extends BaseAdapter {
    SettingsActivity context;
    String[] data;
    private static LayoutInflater inflater = null;
    private AlertDialog loading_alert;
    public final static int QRCodeSize = 500;

    public ListAdapter(Context context, String[] data) {
        // TODO Auto-generated constructor stub
        this.context = (SettingsActivity) context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = inflater.inflate(R.layout.qr_list_item, null);

        Button item = vi.findViewById(R.id.itemButton);
        String[] qrDataFromFile = data[position].split("~");

        String[] qrData = qrDataFromFile[0].split(",");
        Log.d("Stuff", data[position]);
        String teamNumber = qrData[1], matchNumber = qrData[2], qrString = qrDataFromFile[0];

        item.setSelected(qrDataFromFile[1].equals("Y"));
        item.setText("Team Number: " + padLeftZeros(teamNumber, 2) + "    Match Number: " + padLeftZeros(matchNumber, 2));
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder loading_dialog = new AlertDialog.Builder(context);
                View loading_view = context.getLayoutInflater().inflate(R.layout.loading_screen, null);
                loading_alert = loading_dialog.create();
                loading_alert.setView(loading_view);
                loading_alert.setCancelable(false);
                loading_alert.show();

                ListAdapter.QRRunnable qrRunnable = new ListAdapter.QRRunnable(v.getTag().toString().split("~"), context, v);
                new Thread(qrRunnable).start();
            }
        });
        item.setTag(teamNumber + "~" + matchNumber + "~" + qrString);
        item.setId(Integer.parseInt(teamNumber+matchNumber));
        return vi;
    }

    //QR Generation
    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRCodeSize, QRCodeSize, null
            );
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        GenUtils.getAColor(context, R.color.black) : GenUtils.getAColor(context, R.color.white);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    class QRRunnable implements Runnable {

        private String teamNum, matchNum, qrString;
        private SettingsActivity context;
        private View buttonView;

        public QRRunnable(String[] qrData, Context c, View v){
            teamNum = qrData[0];
            matchNum = qrData[1];
            qrString = qrData[2];
            context = (SettingsActivity) c;
            buttonView = v;
        }

        @Override
        public void run() {
            try {
                Bitmap bitmap = TextToImageEncode(qrString);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final AlertDialog.Builder qrDialog = new AlertDialog.Builder(context);
                        View view1 = context.getLayoutInflater().inflate(R.layout.popup_qr_cached, null);
                        ImageView imageView = view1.findViewById(R.id.imageView);
                        TextView teamNumber = view1.findViewById(R.id.TeamNumberQR);
                        TextView matchNumber = view1.findViewById(R.id.MatchNumberQR);
                        Button readButton = view1.findViewById(R.id.readButton);
                        Button unreadButton = view1.findViewById(R.id.unreadButton);
                        imageView.setImageBitmap(bitmap);
                        qrDialog.setView(view1);
                        final AlertDialog dialog = qrDialog.create();
                        dialog.setCancelable(false);

                        teamNumber.setText(GenUtils.padLeftZeros(teamNum, 2));
                        matchNumber.setText(GenUtils.padLeftZeros(matchNum, 2));

                        loading_alert.dismiss();

                        dialog.show();

                        readButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String[] qrList = HashMapManager.setupQRList(context);
                                int qrDataIndex = -1;
                                for(qrDataIndex = 0; qrDataIndex <= qrList.length; qrDataIndex++){
                                    if(qrDataIndex == qrList.length)
                                        throw new IndexOutOfBoundsException("QR String not found in file");
                                    if(qrList[qrDataIndex].split("~")[0].equals(qrString))
                                        break;
                                }
                                qrList[qrDataIndex] = qrString + "~Y";
                                HashMapManager.outputQRList(qrList, context);
                                buttonView.setSelected(true);
                                dialog.dismiss();
                            }
                        });

                        unreadButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String[] qrList = HashMapManager.setupQRList(context);
                                int qrDataIndex = -1;
                                for(qrDataIndex = 0; qrDataIndex <= qrList.length; qrDataIndex++){
                                    if(qrDataIndex == qrList.length)
                                        throw new IndexOutOfBoundsException("QR String not found in file");
                                    if(qrList[qrDataIndex].split("~")[0].equals(qrString))
                                        break;
                                }
                                qrList[qrDataIndex] = qrString + "~N";
                                HashMapManager.outputQRList(qrList, context);
                                buttonView.setSelected(false);
                                dialog.dismiss();
                            }
                        });
                    }
                });
            } catch (WriterException e){}
        }
    }
}
