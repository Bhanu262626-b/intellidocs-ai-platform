function ChatSidebar({
    conversations,
    selectedConversation,
    setSelectedConversation,
    createConversation,
}){

    return (
        <div
        style={{
            width: "250px",
            border:"1px solid gray",
            borderRadius: "10px",
            padding: "10px",
            height:"90vh"
        }}
>
            <h2>Conversations</h2>

           <button
    onClick={() => setSelectedConversation(null)}
>
    + New Chat
</button>  
        {conversations.map((conversation) => (
            <div
                key={conversation.id}
                onClick={() => setSelectedConversation(conversation.id)
                 
                }
                style={{
                        padding: "10px",
                        cursor: "pointer",
                        borderRadius: "5px",
                        marginBottom: "10px",
                        backgroundColor:
                            selectedConversation === conversation.id
                                ? "#dbeafe"
                                : "#ffffff"
                    }}
            >
                💬{conversation.title}
            </div>
        ))} 
</div>
    );
}

export default ChatSidebar;