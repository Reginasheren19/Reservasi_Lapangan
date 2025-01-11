public class LapanganAdapter extends RecyclerView.Adapter<LapanganAdapter.LapanganViewHolder> {

    private List<lapangan> lapanganList;
    private Context context;

    public LapanganAdapter(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.lapanganList = dbHelper.getAllLapangan();
        this.context = context;
    }

    @NonNull
    @Override
    public LapanganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, parent, false);
        return new LapanganViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LapanganViewHolder holder, int position) {
        Lapangan currentLapangan = lapanganList.get(position);
        holder.nama.setText(currentLapangan.getNama());
        holder.lokasi.setText(currentLapangan.getLokasi());
        holder.gambar.setImageResource(currentLapangan.getImageResource());
        holder.rating.setText(String.valueOf(currentLapangan.getRating()));

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("placename", currentLapangan.getNama());
            intent.putExtra("placelocation", currentLapangan.getLokasi());
            intent.putExtra("imagedetails", currentLapangan.getImageResource());
            intent.putExtra("placedetails", "Detail informasi lapangan...");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lapanganList.size();
    }

    public static class LapanganViewHolder extends RecyclerView.ViewHolder {
        TextView nama, lokasi, rating;
        ImageView gambar;
        CardView cardView;

        public LapanganViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.placename);
            lokasi = itemView.findViewById(R.id.placelocation);
            gambar = itemView.findViewById(R.id.imagedetails);
            rating = itemView.findViewById(R.id.placerating);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
