function DocumentSidebar({ documents, selectedDocument, setSelectedDocument, deleteDocument }) {
    console.log(documents);
    return (
    <div
      style={{
        width: "250px",
        border: "1px solid gray",
        padding: "10px",
        borderRadius: "10px",
        height: "90vh",
        overflowY: "auto",
      }}
    >
      <h2>Documents</h2>

      {documents.map((document) => (
  <div
    key={document.id}
    onClick={() => setSelectedDocument(document.id)}
    style={{
      backgroundColor:
      selectedDocument === document.id
        ? "#dbeafe"
        : "#ffffff",
      display: "flex",
      justifyContent: "space-between",
      alignItems: "center",
      padding: "10px",
      marginBottom: "10px",
      border: "1px solid #ddd",
      borderRadius: "5px",
    }}
  >
    <span>📄 {document.fileName}</span>

    <button
      onClick={() => deleteDocument(document.id)}
      style={{
        background: "red",
        color: "white",
        border: "none",
        padding: "5px 10px",
        borderRadius: "5px",
        cursor: "pointer",
      }}
    >
      Delete
    </button>
    </div>
))}
</div>   
    );
}

export default DocumentSidebar;
