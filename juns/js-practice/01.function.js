import { use } from "react";

console.log("hello");

function greet(name, message){
    return `${message}, ${name}`;
}

function greet(name) {
    return `hi ${name}`;
}


console.log(greet("name"));
console.log(greet("name", "hello"));


const greet2 = function(name) {
    return `hello ${name}`
}

console.log(greet2("Hello"));




const user = {
    name: "김철수",
    age: 25
};

// 객체 복사
const userCopy = { ...user };

// 속성 추가
const userWithEmail = { ...user, email: "kim@example.com" };
console.log(userWithEmail);
// { name: "김철수", age: 25, email: "kim@example.com" }

// 속성 덮어쓰기
const olderUser = { ...user, age: 26 };
console.log(olderUser);
// { name: "김철수", age: 26 }



let todos = [
    { id:1, text: "공부하기", done: false },
    { id:2, text: "놀기", done: true },
];

function setTodos(newTodos){
    todos = newTodos;
    console.log("todo updates: :: ", todos);
}


const newItem = { id:3, text: "잠자기", done: true };
setTodos([...todos, newItem])

const filteredTodos = todos.filter(item => item.id !== 2);
setTodos(filteredTodos);


const updatedTodos = todos.map(todo => ({
    ...todo,
    done: !todo.done
}))
setTodos(updatedTodos);


let cartState = [
  { id: 1, productName: "pen", price: 3000, quantity: 1 },
  { id: 2, productName: "note", price: 5000, quantity: 5 },
  { id: 3, productName: "pad", price: 300000, quantity: 2 },
];

//1. id가 1인 상품의 수량을 3으로 바꿔주세요.  
const newCart = cartState.map(item => {
    if (item.quantity === 1){
        return {...item, quantity:3};
    }
    return item
});

console.log(newCart)

//2. 새로운 상품을 하나 추가하세요. 
cartState = [...cartState, { id: 4, productName: "pad2", price: 400000, quantity: 20 }]
console.log(cartState)


//3. id가 3인 상품을 삭제해 주세요.
cartState = cartState.filter(item => item.id != 3);
console.log(cartState)



