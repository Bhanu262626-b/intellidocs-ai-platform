import { useState, useEffect, useRef } from "react";
import axios from "axios";
import DocumentSidebar from "./components/DocumentSidebar";
import UploadSection from "./components/UploadSection";
import ChatSidebar from "./components/ChatSidebar";

function App() {

  const [file, setFile] = useState(null);
  const [question, setQuestion] = useState("");
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(false);
  const [documents, setDocuments] = useState([]);
  const [selectedDocument, setSelectedDocument] = useState(null);
  const [conversations, setConversations] = useState([]);
  const [selectedConversation, setSelectedConversation] = useState(null);

  const messagesEndRef = useRef(null);

  useEffect(() => {

  messagesEndRef.current?.scrollIntoView({
    behavior: "smooth"
  });

}, [messages]);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const uploadFile = async () => {

    if (!file) {
      alert("Please select a file");
      return;
    }

    const formData = new FormData();

    formData.append("file", file);

    try {

      const response = await axios.post(
        "http://localhost:8080/documents/upload",
        formData
      );

      alert("File uploaded successfully!");

      console.log(response.data);

      await loadDocuments();

    } catch (error) {

      console.error(error);

      alert("Upload failed");
    }
  };
  const loadDocuments = async () => {

  try {

    const response = await axios.get(
      "http://localhost:8080/documents"
    );

    setDocuments(response.data);

  } catch (error) {

    console.error(error);
  }
};
const loadConversations = async () => {

    try {

        const response = await axios.get(
            "http://localhost:8080/chat/conversations"
        );
        console.log(response.data);
        setConversations(response.data);

    } catch (error) {

        console.error(error);

    }

};
const createConversation = async () => {

    const title = window.prompt("Conversation title");

    if (!title) return;

    try {

        await axios.post(
            `http://localhost:8080/chat/conversations?title=${title}`
        );

        await loadConversations();

    } catch (error) {

        console.error(error);

    }

};
const loadMessages = async (conversationId) => {

    try {

        const response = await axios.get(
            `http://localhost:8080/chat/conversations/${conversationId}/messages`
        );

        setMessages(response.data);

    } catch (error) {

        console.error(error);

    }

};
const handleConversationSelect = async (conversationId) => {

    setSelectedConversation(conversationId);

    await loadMessages(conversationId);

};
const deleteDocument = async (id) => {

  const confirmed = window.confirm(
    "Are you sure you want to delete this document?"
  );

  if (!confirmed) return;

  try {

    await axios.delete(
      `http://localhost:8080/documents/${id}`
    );

    await loadDocuments();

    alert("Document deleted successfully!");

  } catch (error) {

    console.error(error);

    alert("Delete failed.");
  }
};
  useEffect(() => {

  loadDocuments();
  loadConversations();

  }, []);
  console.log(documents);
  console.log(selectedDocument);
  const askQuestion = async () => {

    if (!selectedConversation) {
        alert("Please select a conversation first.");
        return;
    }
    if (!selectedDocument) {
    alert("Please select a document first.");
    return;
}
    
    setLoading(true);
  try {

    await axios.post(
      "http://localhost:8080/chat",
      {
        conversationId: selectedConversation,
        documentId: selectedDocument,
        message: question
      }
    );


setQuestion("");

await loadMessages(selectedConversation); 
  setLoading(false);

setQuestion("");

  } catch (error) {

   console.error(error);

        setLoading(false);
}
  
};

  return (
  <div
    style={{
      display: "flex",
      gap: "20px",
      padding: "20px",
    }}
  >
   
      <ChatSidebar
      conversations={conversations}
      selectedConversation={selectedConversation}
      setSelectedConversation={handleConversationSelect}
      createConversation={createConversation}
/>
      <DocumentSidebar 
        documents={documents}
        selectedDocument={selectedDocument}
        setSelectedDocument={setSelectedDocument}
        deleteDocument={deleteDocument}
      />
      

      
    
    {/* Main Content */}
    <div
      style={{
        flex: 1,
        display: "flex",
        flexDirection: "column",
      }}
    >
      <h1>Enterprise Knowledge Assistant</h1>

      {/* Upload Section */}
      <UploadSection />
      <div style={{ marginBottom: "20px" }}>
        <input type="file" onChange={handleFileChange} />

        <br />
        <br />

        <button onClick={uploadFile}>
          Upload PDF
        </button>
      </div>

      <hr />

     

      {/* Ask Question Section */}
      <div style={{ marginTop: "20px" }}>
        <h2>Ask a Question</h2>

        <input
          type="text"
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="Ask a question..."
          style={{
            width: "500px",
            padding: "10px",
          }}
        />

        <br />
        <br />

        <button onClick={askQuestion}>
          Ask AI
        </button>
      </div>

      {loading && (
        <p style={{ marginTop: "20px" }}>
          AI is thinking...
        </p>
      )}

      {/* Chat Window */}
      <h3 style={{ marginTop: "30px" }}>
        Conversation
      </h3>

      <div
        style={{
          border: "1px solid gray",
          borderRadius: "10px",
          padding: "15px",
          height: "500px",
          overflowY: "auto",
        }}
      >
        {messages.map((chat, index) => (
          <div
            key={index}
            >
            <div
            style={{
                display: "flex",
                justifyContent: "flex-end",
                marginBottom: "10px"
            }}
        >
            <div
                style={{
                    backgroundColor: "#DCF8C6",
                    padding: "10px",
                    borderRadius: "10px",
                    maxWidth: "70%"
                }}
            >
                <strong>You</strong>

                <p>{chat.message}</p>

            </div>
        </div>

        {/* AI */}

        <div
            style={{
                display: "flex",
                justifyContent: "flex-start",
                marginBottom: "10px"
            }}
        >
            <div
                style={{
                    backgroundColor: "#F1F0F0",
                    padding: "10px",
                    borderRadius: "10px",
                    maxWidth: "70%"
                }}
            >
                <strong>AI</strong>

                <p>{chat.response}</p>

            </div>
        </div>

    </div>
        ))}

        <div ref={messagesEndRef}></div>
      </div>
    </div>
  </div>
);
}

export default App;