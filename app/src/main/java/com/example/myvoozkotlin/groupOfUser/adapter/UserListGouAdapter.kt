package com.example.myvoozkotlin.groupOfUser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.homelibrary.model.UserShort
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.data.db.realmModels.AuthUserModel
import com.example.myvoozkotlin.databinding.ItemSearchBinding
import com.example.myvoozkotlin.databinding.ItemUserBinding
import com.example.myvoozkotlin.groupOfUser.ChangeNameDialogFragment
import com.example.myvoozkotlin.groupOfUser.helpers.OnUserListItemPicked
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.search.helpers.OnSearchItemPicked

class UserListGouAdapter(private var userItems: List<UserShort>, private val authUserModel: AuthUserModel, private val onUserListItemPicked: OnUserListItemPicked): RecyclerView.Adapter<UserListGouAdapter.IntercomViewHolder>() {

    inner class IntercomViewHolder(val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercomViewHolder {
        return IntercomViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: IntercomViewHolder, position: Int) {
        val binding = holder.binding
        val item = userItems[position]

        binding.tvTitle.text = item.lastName + " " + item.firstName

        binding.cvSettingBtn.setOnClickListener {
            onUserListItemPicked.onUserListItemClick(item)
        }

        if(authUserModel.groupOfUser!!.idOlder == authUserModel.id){
            binding.cvSettingBtn.show()
        }
        else{
            binding.cvSettingBtn.hide()
        }

        if(authUserModel.id == item.id){
            binding.cvSettingBtn.hide()
        }
        else{
            binding.cvSettingBtn.show()
        }



        Glide.with(holder.itemView.context)
            .load(item.photo)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade(HomeFragment.ANIMATE_TRANSITION_DURATION))
            .into(binding.ivPhotoUser)
    }

    fun update(news: List<UserShort>) {
        this.userItems = news
        notifyDataSetChanged()
    }

    fun removeUser(idUser: Int){
        userItems = userItems.filter { it.id != idUser }
        notifyDataSetChanged()
    }

    override fun getItemCount() = userItems.size
}