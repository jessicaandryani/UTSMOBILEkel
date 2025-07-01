package com.jess.utsmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.NumberFormat;
import java.util.Locale;

public class LaporanFragment extends Fragment {

    // Deklarasi semua komponen UI
    private WebView webView;
    private DatabaseHelper db;
    private TextView tvTotalPemasukan, tvTotalPengeluaran, tvSaldoAkhir; // Variabel baru

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laporan, container, false);

        // Menghubungkan variabel dengan ID dari layout XML
        webView = view.findViewById(R.id.webView);
        tvTotalPemasukan = view.findViewById(R.id.tv_total_pemasukan);
        tvTotalPengeluaran = view.findViewById(R.id.tv_total_pengeluaran);
        tvSaldoAkhir = view.findViewById(R.id.tv_saldo_akhir);

        db = new DatabaseHelper(getContext());

        // Memanggil metode untuk memuat semua data ke tampilan
        loadGoogleCharts();
        updateSummary();

        return view;
    }

    /**
     * Metode untuk memuat Pie Chart ke dalam WebView.
     */
    private void loadGoogleCharts() {
        double totalPemasukan = db.getTotalPemasukan();
        double totalPengeluaran = db.getTotalPengeluaran();

        // Template HTML untuk Google Charts
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        google.charts.load('current', {'packages':['corechart']});\n" +
                "        google.charts.setOnLoadCallback(drawChart);\n" +
                "        function drawChart() {\n" +
                "            var data = google.visualization.arrayToDataTable([\n" +
                "                ['Kategori', 'Jumlah'],\n" +
                "                ['Pemasukan', " + totalPemasukan + "],\n" +
                "                ['Pengeluaran', " + totalPengeluaran + "]\n" +
                "            ]);\n" +
                "\n" +
                "            var options = {\n" +
                "                title: '',\n" + // Judul di HTML bisa dikosongkan karena sudah ada di layout utama
                "                pieHole: 0.4,\n" +
                "                legend: 'none',\n" + // Menyembunyikan legenda agar lebih bersih
                "                chartArea: {left:10, top:10, width:'90%', height:'90%'}\n" + // Atur area chart
                "            };\n" +
                "\n" +
                "            var chart = new google.visualization.PieChart(document.getElementById('donutchart'));\n" +
                "            chart.draw(data, options);\n" +
                "        }\n" +
                "    </script>\n" +
                "    <style>\n" +
                "        body, html { height: 100%; margin: 0; display: flex; justify-content: center; align-items: center; }\n" +
                "        #donutchart { width: 100%; height: 100%; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id=\"donutchart\"></div>\n" +
                "</body>\n" +
                "</html>";

        // Pengaturan WebView
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Menggunakan loadDataWithBaseURL agar bisa memuat script eksternal
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    /**
     * Metode baru untuk menghitung dan menampilkan ringkasan saldo.
     */
    private void updateSummary() {
        double totalPemasukan = db.getTotalPemasukan();
        double totalPengeluaran = db.getTotalPengeluaran();
        double saldoAkhir = totalPemasukan - totalPengeluaran;

        // Mengatur format angka menjadi format mata uang Rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        formatRupiah.setMaximumFractionDigits(0); // Menghilangkan angka di belakang koma

        // Mengisi data ke TextView
        tvTotalPemasukan.setText(formatRupiah.format(totalPemasukan));
        tvTotalPengeluaran.setText(formatRupiah.format(totalPengeluaran));
        tvSaldoAkhir.setText(formatRupiah.format(saldoAkhir));
    }
}
