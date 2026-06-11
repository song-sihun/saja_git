// 모든 상품 불러오기
function fetchTodos() {
    fetch('/api/todos')
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById('todoList');
            list.innerHTML = ''; // 기존 리스트 초기화
            data.forEach(todo => {
                const item = document.createElement('li');
                item.innerHTML = `Id: ${todo.id}, Todo: ${todo.description}
                                  <button onclick="editTodo(${todo.id}, '${todo.description}')">Edit</button>
                                  <button onclick="deleteTodo(${todo.id})">Delete</button>`;
                list.appendChild(item);
            });
        })
        .catch(error => console.error('Error fetching products:', error));
}


// 상품 추가하기 (프론트엔드 검증 추가)
function addTodo() {
    const description = document.getElementById('description').value.trim();

    // 입력값 검증
    if (!description) {
        alert("올바르게 입력하세요!");
        return;
    }

    fetch('/api/todos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({description: description })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Todo 추가 실패");
            }
            return response.json();
        })
        .then(() => {
            fetchTodos(); // 상품 목록 새로고침
            document.getElementById('description').value = ''; // 입력 필드 초기화
        })
        .catch(error => console.error('Error adding Todo:', error));
}


// 상품 수정하기
function editTodo(id, oldDescription) {
    const newDescription = prompt("Enter new todo:", oldDescription);

    if (newDescription !== null) {
        fetch(`/api/todos/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({description: newDescription })
        })
            .then(response => response.json())
            .then(() => fetchTodos())
            .catch(error => console.error('Error updating todo:', error));
    }
}

// 상품 삭제하기
function deleteTodo(id) {
    if (confirm("Are you sure you want to delete this todo?")) {
        fetch(`/api/todos/${id}`, { method: 'DELETE' })
            .then(() => fetchTodos()) // 상품 목록 새로고침
            .catch(error => console.error('Error deleting todo:', error));
    }
}

// 페이지 로드 시 모든 상품 불러오기
document.addEventListener('DOMContentLoaded', function () {
    fetchTodos();
});



//function fetchProducts() {
//    fetch('/api/products')
//        .then(response => response.json())
//        .then(data => {
//            const list = document.getElementById('productList');
//            list.innerHTML = '';
//            data.forEach(product => {
//                const item = document.createElement('li');
//                item.textContent = `Name: ${product.name}, Price: ${product.price}`;
//                list.appendChild(item);
//            });
//        });
//}
//function addProduct() {
//    const name = document.getElementById('productName').value;
//    const price = document.getElementById('productPrice').value;
//
//    fetch('/api/products', {
//        method: 'POST',
//        headers: {
//            'Content-Type': 'application/json'
//        },
//        body: JSON.stringify({ name: name, price: price })
//    }).then(() => {
//        fetchProducts();  // Refresh the list
//        document.getElementById('productName').value = '';  // Clear the input
//        document.getElementById('productPrice').value = '';  // Clear the input
//    });
//}
//
//document.addEventListener('DOMContentLoaded', function () {
//    fetchProducts();
//});