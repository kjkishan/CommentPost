package com.example.kishanjha.commentpost;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    List<Comment> myLis =new ArrayList <> (  );
    CommentId commentId;

    public CommentAdapter(List <Comment> myLis , CommentId commentId) {
        this.myLis = myLis;
        this.commentId = commentId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View view= LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.single_item_comment,parent,false );
        return new ViewHolder ( view );

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder , int position) {
        final Comment myComm=myLis.get ( position );
        holder.cnam.setText ( myComm.getName () );
        holder.cpos.setText ( myComm.getComments () );
        holder.ctme.setText ( myComm.getTimes () );
    }

    @Override
    public int getItemCount() {
        return myLis.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cnam,cpos,ctme;
        public ViewHolder(View itemView) {
            super ( itemView );
            cnam=itemView.findViewById ( R.id.Cname );
            cpos=itemView.findViewById ( R.id.Cpost );
            ctme=itemView.findViewById ( R.id.Ctime );
        }
    }

    public interface CommentId{
        public void getCommentId(String id);
    }
}
