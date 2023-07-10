package com.example.evaluacion3.recyclerview;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.evaluacion3.R;
import com.example.evaluacion3.modelo.Automovil;

public class AutomovilAdapter extends RecyclerView.Adapter<AutomovilAdapter.AutomovilViewHolder>{

    private  List<Automovil> automoviles;
    private OnItemClickListener listener;

    public AutomovilAdapter(List<Automovil> automoviles) {
            this.automoviles = automoviles;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public void agregarAutomovil(Automovil automovil) {
        automoviles.add(automovil);
        notifyItemInserted( automoviles.size() - 1);
    }


    public void removerAtomovil(Automovil automovil) {
        automoviles.remove(automovil);
        notifyDataSetChanged();
    }


    public void modificarAutomovil(Automovil automovil, int posicion) {
        automoviles.set(posicion, automovil);
        notifyItemChanged(posicion);
    }


    public interface OnItemClickListener {
        void onEditarClick(Automovil automovil, int posicion);
        void onEliminarClick(Automovil automovil, int posicion);
    }



    public class AutomovilViewHolder extends RecyclerView.ViewHolder {
        private TextView etCodigo;
        private TextView etModelo;
        private TextView etPatente;
        private TextView etPrecio;

        public AutomovilViewHolder(View view) {
            super(view);
            etCodigo     = (TextView) view.findViewById(R.id.etCodigo);
            etModelo = (TextView) view.findViewById(R.id.etModelo);
            etPatente  = (TextView) view.findViewById(R.id.etPatente);
            etPrecio  = (TextView) view.findViewById(R.id.etPrecio);

            ImageButton btnEditarLibro      = (ImageButton) view.findViewById(R.id.btnEditarAutomovil);
            ImageButton btnEliminarLibro    = (ImageButton) view.findViewById(R.id.btnEliminarAutomovil);

            btnEliminarAutomovil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener != null ) {
                        int posicion = getAdapterPosition();
                        if( posicion != RecyclerView.NO_POSITION) {
                            Automovil automovil = automoviles.get(posicion);
                            listener.onEliminarClick(automovil, posicion);
                        }
                    }
                }
            });

            btnEditarAtomovil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener != null ) {
                        int posicion = getAdapterPosition();
                        if( posicion != RecyclerView.NO_POSITION) {
                            Automovil automovil = automoviles.get(posicion);
                            listener.onEditarClick(automovil, posicion);
                        }
                    }
                }
            });
        }

        public TextView getEtCodigo() {
            return etCodigo;
        }

        public TextView getEtModelo() {
            return etModelo;
        }

        public TextView getEtPatente() {
            return etPatente;
        }
        public TextView getEtPrecio() {
            return etPrecio;
        }
    }

    @NonNull
    @Override
    public AutomovilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.automovil_row_item, parent, false);
        return new AutomovilViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AutomovilViewHolder viewHolder, int position) {
        viewHolder.getEtCodigo().setText( automoviles.get(position).getCodigo().toString() );
        viewHolder.getEtModelo().setText( automoviles.get(position).getModelo() );
        viewHolder.getEtPatente().setText( automoviles.get(position).getPatente() );
        viewHolder.getEtPrecio().setText( automoviles.get(position).getPrecio() );
    }

    @Override
    public int getItemCount() {
        return automoviles.size();
    }

}
