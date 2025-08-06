package com.dilsahozkan.manageusersapp.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dilsahozkan.manageusersapp.data.remote.dto.AddressDto
import com.dilsahozkan.manageusersapp.data.remote.dto.CompanyDto

@BindingAdapter("user:address")
fun setFullAddress(textView: TextView, address: AddressDto?) {
    address?.let {
        val parts = listOfNotNull(it.street, it.suite, it.city, it.zipcode)
        textView.text = parts.joinToString(", ")
    }
}


@BindingAdapter("user:company")
fun setCompanyInfo(textView: TextView, company: CompanyDto?){
    company?.let {
        val parts = listOfNotNull(it.name, it.catchPhrase, it.bs)
        textView.text = parts.joinToString(separator = "\n")
    }
}


