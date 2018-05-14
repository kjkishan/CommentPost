package com.example.kishanjha.commentpost;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<Post> loadPosts = new ArrayList <> (  );
    PostIdListener postIdListener;


    public PostAdapter(List <Post> loadPosts, PostIdListener postIdListener) {
        this.loadPosts = loadPosts;
        this.postIdListener=postIdListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.single_item_main,parent,false );
        return new ViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder , int position) {
        final Post myPos =loadPosts.get ( position );
        holder.name.setText ( myPos.getName () );
        holder.post.setText ( myPos.getPost () );
        holder.time.setText ( myPos.getTime () );
        holder.edit.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                postIdListener.editPost ( myPos.getId () );

            }
        } );
        holder.comment.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                postIdListener.commentPost ( myPos.getId () );
            }
        } );
        holder.delet.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                postIdListener.deletePost ( myPos.getId () );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return loadPosts.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,post,time;
        Button edit,comment,delet;
        public ViewHolder(View itemView) {
            super ( itemView );
            name=itemView.findViewById ( R.id.Name );
            post=itemView.findViewById ( R.id.Post );
            time=itemView.findViewById ( R.id.Time );
            edit=itemView.findViewById ( R.id.EditPost );
            comment=itemView.findViewById ( R.id.Comment );
            delet=itemView.findViewById ( R.id.Delete );

        }
    }
    public interface PostIdListener{
          void editPost(String id);
         void deletePost(String id);
         void commentPost(String id);
    }
}
