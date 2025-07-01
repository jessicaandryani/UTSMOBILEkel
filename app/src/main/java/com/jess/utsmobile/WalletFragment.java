package com.jess.utsmobile;

// MainActivity.java
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WalletFragment extends Fragment {

    public class WebAppInterface {
        private String ChartData;

        public WebAppInterface(String chartData) {
            this.ChartData = chartData;
        }

        @JavascriptInterface
        public String getChartData() {
            return ChartData;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        WebView webView = view.findViewById(R.id.webView);
        webView.addJavascriptInterface(new WebAppInterface(getChartDataFromDB()), "Android");

        webView.setWebViewClient(new WebViewClient()); // Menghindari pembukaan di browser eksternal

        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true); // Izinkan DOM storage untuk Google Charts
        webView.getSettings().setAllowFileAccess(true);

        // Pasang JavascriptInterface untuk berkomunikasi dengan chart.html
        webView.addJavascriptInterface(
                new WebAppInterface(getChartDataFromDB()), "Android"
        );

        Log.d("WalletFragment", "Loading URL: file:///android_asset/chart.html");

        // Perbaikan: Gunakan path yang benar untuk file lokal
        webView.loadUrl("file:///android_asset/chart.html");

        return view;
    }

    private String getChartDataFromDB() {
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        ArrayList<TransaksiModel> transaksiList = dbHelper.getAllTransaksi();

        JSONArray jsonArray = new JSONArray();
        for (TransaksiModel transaksi : transaksiList) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("kategori", transaksi.getKategori());
                jsonObject.put("jumlah", transaksi.getJumlah());
                jsonObject.put("tipe", transaksi.getJenis());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("WalletFragment", "JSON Data: " + jsonArray.toString());
        return jsonArray.toString();
    }
}
