package com.mallzhub.customer.ui.live_chat

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.BotFragmentBinding
import com.mallzhub.customer.models.LiveChatMessage
import com.mallzhub.customer.ui.LogoutHandlerCallback
import com.mallzhub.customer.ui.NavDrawerHandlerCallback
import com.mallzhub.customer.ui.common.BaseFragment
import com.mallzhub.customer.util.getCurrentDateTime
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class BotFragment : BaseFragment<BotFragmentBinding, LiveChatViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_bot
    override val viewModel: LiveChatViewModel by viewModels {
        viewModelFactory
    }

    private var listener: LogoutHandlerCallback? = null

    private var drawerListener: NavDrawerHandlerCallback? = null

    private lateinit var chatBotMessageAdapter: ChatBotMessageAdapter

    private lateinit var chatSuggestionListAdapter: ChatSuggestionListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LogoutHandlerCallback) {
            listener = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }

        if (context is NavDrawerHandlerCallback) {
            drawerListener = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        drawerListener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        val chatSuggestions = listOf("Hi", "Red Color", "T-Shirt", "Money Back", "Product Return", "Yellow Hat")
        chatSuggestionListAdapter = ChatSuggestionListAdapter(appExecutors) {
            chatBotMessageAdapter.addMessage(LiveChatMessage(chatBotMessageAdapter.getMessageCount(), it, getCurrentDateTime(), 0))
        }

        viewDataBinding.rvChatSuggestion.adapter = chatSuggestionListAdapter
        chatSuggestionListAdapter.submitList(chatSuggestions)

        chatBotMessageAdapter = ChatBotMessageAdapter {

        }
        viewDataBinding.messageRecycler.adapter = chatBotMessageAdapter

        viewDataBinding.btnSend.setOnClickListener {

            if (viewModel.newMessage.value?.get(0) == '0') {
                chatBotMessageAdapter.addMessage(LiveChatMessage(chatBotMessageAdapter.getMessageCount(), viewModel.newMessage.value?.removePrefix("0"), getCurrentDateTime(), 0))
            } else {
                chatBotMessageAdapter.addMessage(LiveChatMessage(chatBotMessageAdapter.getMessageCount(), viewModel.newMessage.value, getCurrentDateTime(), 1))
            }

            viewModel.newMessage.postValue("")
            //chatSuggestionListAdapter.resetList()
        }

//        viewModel.chatMessages.observe(viewLifecycleOwner, androidx.lifecycle.Observer { orderItems ->
//            orderItems?.let {
//                viewDataBinding.messageRecycler = total.toString()
//            }
//        })
    }
}