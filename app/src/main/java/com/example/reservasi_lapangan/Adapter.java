import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LapanganAdapter extends RecyclerView.Adapter<LapanganAdapter.LapanganViewHolder> {

    private List<Lapangan> lapanganList;

    // Constructor
    public LapanganAdapter(List<Lapangan> lapanganList) {
        this.lapanganList = lapanganList;
    }

    @Override
    public LapanganViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lapangan, parent, false);
        return new LapanganViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LapanganViewHolder holder, int position) {
        Lapangan lapangan = lapanganList.get(position);
        holder.namaLapangan.setText(lapangan.getNamaLapangan());
        holder.lokasi.setText(lapangan.getLokasi());
        holder.harga.setText("Rp " + lapangan.getHarga());
        // Set gambar atau deskripsi sesuai kebutuhan
    }

    @Override
    public int getItemCount() {
        return lapanganList.size();
    }

    public class LapanganViewHolder extends RecyclerView.ViewHolder {
        public TextView namaLapangan, lokasi, harga;

        public LapanganViewHolder(View view) {
            super(view);
            namaLapangan = view.findViewById(R.id.namaLapangan);
            lokasi = view.findViewById(R.id.lokasi);
            harga = view.findViewById(R.id.harga);
        }
    }
}
