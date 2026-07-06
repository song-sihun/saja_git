
const double = x => x * 2;

const greet3 = name => "안녕하세요" + name + "님";

const add = (a, b) => a + b;

const getReandomNumber = () => Math.random();


const createPerson = (name, age) => ({name:name, age:age});

console.log(createPerson("김철수", 25))

const createTodo = text => ({id:Date.now(), text:text, completed:false});

console.log(createTodo("React 공부하기"))

const product = {
    id: 101,
    name: "맥북 프로",
    price: 2500000,
    brand: "Apple",
    specs: {
        cpu: "M3 Pro",
        ram: "18GB",
        storage: "512GB"
    }
};

const { id, name, price } = product;

console.log(id, name, price)

const { name:productName } = product;
console.log(productName);


const { discount = 0 } = product;
console.log(discount);

const { specs : {
    cpu, ram
} } = product;
console.log(cpu, ram);


const scores = [95, 88, 76, 92, 85];

const [ first, second ] = scores;
console.log(first, second);

const [ num1, ...nums ] = scores;
console.log(num1, nums);

const [ number1, , number3, , number5 ] = scores;
console.log(number1, number3, number5);



const user = {
    name: "김철수",
    age: 25,
    city: "서울"
};

const hobbies = ["독서", "게임", "운동"];

// 문제 5-1: user 객체를 복사하고, email 속성을 추가하세요
const newUser = {
    ...user,
    email:"kim@example.com"
};

console.log(newUser);

// 결과: { name: "김철수", age: 25, city: "서울", email: "kim@example.com" }


// 문제 5-2: user 객체에서 age만 26으로 변경한 새 객체를 만드세요

const newUser1 = {
    ...user,
    age:26
};

console.log(newUser1);


// 문제 5-3: hobbies 배열 앞에 "코딩"을 추가한 새 배열을 만드세요
const newHobbies = ["코딩", ...hobbies]
console.log(newHobbies);


// 문제 5-4: 두 배열을 합치세요
const morningTasks = ["기상", "아침식사"];
const eveningTasks = ["저녁식사", "취침"];

const mergedTasks = [...morningTasks, ...eveningTasks];
console.log(mergedTasks)
// 결과: ["기상", "아침식사", "저녁식사", "취침"]



const numbers = [1, 2, 3, 4, 5];

const users = [
    { id: 1, name: "김철수", age: 25 },
    { id: 2, name: "이영희", age: 30 },
    { id: 3, name: "박민수", age: 22 }
];

// 문제 6-1: numbers의 각 요소를 제곱한 새 배열을 만드세요
const newNumbers = numbers.map(num => num * num);
console.log(newNumbers);
// 결과: [1, 4, 9, 16, 25]

// 문제 6-2: users에서 이름만 추출한 배열을 만드세요
const names = users.map(user => user.name);
console.log(names);
// 결과: ["김철수", "이영희", "박민수"]

// 문제 6-3: users에서 각 객체에 isAdult 속성을 추가하세요 (age >= 25면 true)
const users2 = users.map((user) => user.age > 25 ? {...user, isAdult:true} : {...user, isAdult:false});
console.log(users2);
// 결과: [{ id: 1, name: "김철수", age: 25, isAdult: true }, ...]



const products = [
    { id: 1, name: "노트북", price: 1500000, inStock: true },
    { id: 2, name: "마우스", price: 50000, inStock: false },
    { id: 3, name: "키보드", price: 150000, inStock: true },
    { id: 4, name: "모니터", price: 500000, inStock: true },
    { id: 5, name: "헤드셋", price: 200000, inStock: false }
];

// 문제 7-1: 재고가 있는 상품만 필터링하세요
console.log(products.filter(item => item.inStock));

// 문제 7-2: 가격이 200000원 이하인 상품만 필터링하세요
console.log(products.filter(item => item.price <= 200000));

// 문제 7-3: 재고가 있고 가격이 500000원 이하인 상품만 필터링하세요
console.log(products.filter(item => item.price <= 500000 && item.inStock));

// 문제 7-4: id가 3인 상품을 제외한 나머지 상품을 필터링하세요 (삭제 시뮬레이션)
console.log(products.filter(item => item.id != 3));


const students = [
    { id: 1, name: "김철수", score: 85, grade: "A" },
    { id: 2, name: "이영희", score: 92, grade: "A" },
    { id: 3, name: "박민수", score: 78, grade: "B" },
    { id: 4, name: "정수진", score: 95, grade: "A" },
    { id: 5, name: "홍길동", score: 65, grade: "C" }
];

// 문제 8-1: grade가 "A"인 학생들의 이름만 추출하세요
console.log(students.filter(student => student.grade === "A"));
// 결과: ["김철수", "이영희", "정수진"]

// 문제 8-2: score가 80점 이상인 학생들의 점수를 10점씩 올린 새 배열을 만드세요
const newStudents = students.filter(student => student.score >= 80).map((student) => student.score >= 80 ? {...student, score:student.score + 10 } : "");
console.log(newStudents);

// 문제 8-3: grade가 "A"인 학생들의 평균 점수를 계산하세요
const avgScore = students.filter(student => student.grade === "A").map((student) => student.score).reduce((sum, current) => sum + current, 0) / students.filter(student => student.grade === "A").length; 
console.log(avgScore);


const user1 = {
    name: "김철수",
    age: 25,
    profile: {
        bio: "개발자입니다",
        avatar: "/images/kim.jpg"
    }
};

const user2 = {
    name: "이영희",
    age: 17
    // profile이 없음
};

const user3 = null;

// 문제 9-1: user1의 age가 20 이상이면 "성인", 아니면 "미성년자"를 출력하세요
const temp1 = user1.age > 19 ? "성인" : "미성년자";
console.log(temp1);

// 문제 9-2: user1의 profile.bio가 있으면 출력하고, 없으면 "소개 없음"을 출력하세요
const temp2 = user1.profile.bio ? user1.profile.bio : "소개 없음";
console.log(temp2);

// 문제 9-3: user2의 profile?.avatar를 안전하게 접근하고, 없으면 기본 이미지 경로를 사용하세요
const temp3 = user2.profile?.avatar ? user1.profile.avatar : "baseurl";
console.log(temp3);

// 문제 9-4: user3의 name을 안전하게 접근하세요 (에러 없이)
console.log(user3?.name ? user3.name : "No name");




const todos = [
    { id: 1, text: "React 공부하기", completed: false },
    { id: 2, text: "JavaScript 복습하기", completed: true },
    { id: 3, text: "프로젝트 시작하기", completed: false },
    { id: 4, text: "Git 커밋하기", completed: true }
];

// 문제 10-1: 새로운 Todo를 추가하는 함수
// addTodo(todos, "운동하기") 호출 시 새 Todo가 추가된 배열 반환
// 새 Todo의 id는 Date.now(), completed는 false

const addTodo = (todos, text) => [...todos, { id:Date.now(), text:text, completed:false }];
console.log(addTodo(todos, "운동하기"));


// 문제 10-2: 특정 id의 Todo를 삭제하는 함수
// deleteTodo(todos, 2) 호출 시 id가 2인 Todo가 제거된 배열 반환
const deleteTodo = (todos, id) => todos.filter(todo => todo.id !== id);
console.log(deleteTodo(todos, 2));

// 문제 10-3: 특정 id의 Todo completed 상태를 토글하는 함수
// toggleTodo(todos, 1) 호출 시 id가 1인 Todo의 completed가 반전된 배열 반환
const toggleTodo = (todos, id) => todos.filter(todo => todo.id === id).map((todo) => {return {...todo, completed:!todo.completed}} );
console.log(toggleTodo(todos, 1));

// 문제 10-4: 완료된 Todo 개수를 반환하는 함수
// getCompletedCount(todos) 호출 시 완료된 Todo 개수 반환
const getCompletedCount = (todos) => todos.filter(todo => todo.completed).length;
console.log(getCompletedCount(todos));

// 문제 10-5: 완료되지 않은 Todo만 반환하는 함수
// getActiveTodos(todos) 호출 시 completed가 false인 Todo 배열 반환
const getActiveTodos = (todos) => todos.filter(todo => todo.completed).length;
console.log(getActiveTodos(todos));



