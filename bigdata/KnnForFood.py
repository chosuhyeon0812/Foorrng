import pandas as pd
from sklearn.neighbors import NearestNeighbors
import numpy as np
import matplotlib.pyplot as plt
import random, json
import mysql.connector
from dotenv import load_dotenv
import os 



#  KNN 이랑 시계열, 시설 데이터 합치고
# 데이터를 리스트로 뽑는다.
features = np.array([
    [2, 3, 5, 7, 1],  # 덮밥 (Rice Bowl)
    [5, 4, 6, 9, 2],  # 전기구이통닭 (Electric Grilled Whole Chicken)
    [4, 2, 5, 8, 1],  # 꼬치 (Skewer)
    [2, 3, 4, 6, 7],  # 타코야끼 (Takoyaki)
    [4, 3, 5, 8, 2],  # 타코 & 케밥 (Taco & Kebab)
    [5, 3, 6, 3, 7],  # 분식 (Korean Street Food)
    [1, 6, 3, 1, 9],  # 빵 (Bread)
    [1, 4, 2, 1, 0],  # 구황작물 (Famine Crops)
    [0, 8, 1, 0, 6],  # 카페 & 디저트 (Cafe & Dessert)
    [5, 5, 5, 5, 5],  # 기타 (Etc)
])
H = np.array(
    [[2, 2, 3, 5, 8], # Rice Bowl
    [2, 3, 5, 9, 0], # Electric Grilled Whole Chicken
    [3, 2, 4, 8, 0], # Skewer
    [1, 3, 4, 4, 7], # Takoyaki
    [3, 2, 5, 7, 6], # Taco & Kebab
    [6, 2, 6, 2, 10], # Formula (unknown)
    [0, 2, 3, 0, 10], # Bread
    [0, 1, 2, 0, 9], # Old Crop
    [1, 8, 2, 0, 5], # Cafe & Dessert
    [5, 5, 5, 5, 5]
])
C = np.array([
    [2, 3, 5, 7, 1],  # 덮밥
    [5, 4, 6, 9, 2],  # 전기구이통닭
    [4, 2, 5, 8, 1],  # 꼬치
    [2, 3, 4, 6, 7],  # 타코야끼
    [4, 3, 5, 8, 2],  # 타코 & 케밥
    [5, 3, 6, 3, 7],  # 분식
    [1, 6, 3, 1, 9],  # 빵
    [1, 4, 2, 1, 0],  # 구황작물
    [0, 8, 1, 0, 6],  # 카페 & 디져트
    [5, 5, 5, 5, 5]  #기타
])
D = np.array([
    [2, 3, 5, 7, 1],  # 덮밥
    [5, 4, 6, 9, 2],  # 전기구이통닭
    [4, 2, 5, 8, 1],  # 꼬치
    [2, 3, 4, 6, 7],  # 타코야끼
    [4, 3, 5, 8, 2],  # 타코 & 케밥
    [5, 3, 6, 3, 7],  # 분식
    [1, 6, 3, 1, 9],  # 빵
    [1, 4, 2, 1, 0],  # 구황작물
    [0, 8, 1, 0, 8],  # 카페 & 디져트
    [5, 5, 5, 5, 5]  #기타
])
E = np.array([
#[spicy, sweet, salt, meat, flour]
    [2, 3, 5, 7, 1],  # 덮밥
    [5, 4, 6, 9, 2],  # 전기구이통닭
    [4, 2, 5, 8, 1],  # 꼬치
    [2, 3, 4, 6, 7],  # 타코야끼
    [4, 3, 5, 8, 2],  # 타코 & 케밥
    [6, 1, 8, 4, 9],  # 분식
    [0, 8, 3, 2, 10],  # 빵
    [2, 5, 2, 3, 0],  # 구황작물
    [0, 10, 3, 0, 8],  # 카페 & 디져트
    [5, 5, 5, 5, 5]  #기타
])

# print(C+H+X+features)

# 데이터 쎗 결합
# combined_features = C + H + X + features 도 되는데 이건 배열을 걍 더하는 거라 수직 방향으로 배열을 이어주는 vstack 메소드를 이용 했다 .
combined_features = np.vstack([C,H,features,D,E])

# 모델 학습 지도 기반인 knn은 게으른 학습기이다. 판별 함수가 아닌 훈련 데이터 셋을 메모리에 저장하는 구조이기 떄문. 따라서 학습이 아니다..!
# 걍 통계를 위한 저장 용도일 뿐.
# n_negithbors 는 홀수로 사용하는 것을 추천한댄다
knn = NearestNeighbors(n_neighbors=5, metric='euclidean')
knn.fit(combined_features)


#<--여기까진 KNN을 통한 유사도 통계를 낸 거기 떄문에 매번 할 필요 없이 따로 저장을 해둔다면 좋을 듰?


# Step 0: KNN을 활용한 값 추천
## 2차원 표로 보여주기 위한 시각화를 위한 scatter plot 임
## 재밌는건 속성이 여러개이기 때문에 5개 중 2개만 택해서 각각의 축에 매핑시키고 볼 수 있음
## knn의 kneighbors를 쓰게 되면, 알아서 비슷한 그룹으로 찾아내줌
labels = ['덮밥', '전기구이통닭', '꼬치', '타코야끼', '타코 & 케밥',
          '분식', '빵', '구황작물', '디저트 & 카페', '기타'] * 5
# # Colors for each food category (repeated for C, H, X datasets)
colors = ['blue', 'green', 'red', 'cyan', 'magenta', 'yellow', 'black', 'orange', 'purple', 'brown'] * 5
# Plotting salt (3rd feature) vs meat (4th feature)
#[spicy, sweet, salt, meat, flour]
# plt.figure(figsize=(10, 8))
# for i in range(len(combined_features)):
#     plt.scatter(combined_features[i, 0], combined_features[i, 1], label=labels[i] if i < 10 else "", color=colors[i], alpha=0.6)
# plt.xlabel('1')
# plt.ylabel('2')
# plt.title('Food Characteristics')
# plt.legend()
# plt.show()
### 2차원 표로 보여주기 위한 라이브러링~ 로직~~ 끘


csv_df = pd.read_csv(r'flowPeople2.csv', encoding='cp949') # csv 파일을 저장했다.


# '동' 컬럼에서 '신천'을 포함하는 행의 인덱스를 가져옵니다.
shincheon_indices = csv_df[csv_df['동'].str.contains('신천')].index
shinahm_indices = csv_df[csv_df['동'].str.contains('신암')].index

# 해당 행의 '동' 값을 '신천동'으로 변경합니다.
csv_df.loc[shincheon_indices, '동'] = '신천동'
csv_df.loc[shinahm_indices, '동'] = '신암동'

grouped_df = csv_df.groupby('동', as_index=False)['상대값'].sum()

data_array_flow_town = grouped_df[['동', '상대값']].to_numpy()





# Input Item
# 해당 동네에 살고 있는 사용자들의 선호 음식 입력 데이터 값을 입력하고 앙상블에 올리기 위한 데이터를 추가한다.
# 곡물 치킨, 치킨 에 대해 저장을 해보자.
#[spicy, sweet, salt, meat, flour]


# 점주가 판매하는 상품이 만약에 Takoyaki라면

result = {}

# 10가지 음식 전부 돌리기

food_truck_manager_items = ["덮밥", "전기구이통닭", "꼬치", "타코야끼", "타코 & 케밥", "분식", "빵", "구황작물", "디저트 & 카페", "기타"]

for food_truck_manager_item in food_truck_manager_items:


    #10개의 점수를 담는 배열 추가 .
    food_truck_manager_item_scores = {}


    cnt = 0

    for row in data_array_flow_town:
        # same : 동이름, relative_value : 하차인원비율
        same, relative_value = row[0], float(row[1])
     
        
        user_preferences = []

        if cnt < 10:
            for i in range(100):
                tmp = np.array([[random.randint(0, 5), random.randint(3, 6), random.randint(4, 7), random.randint(2, 10), random.randint(4, 7)]])
                user_preferences.append(tmp)
        elif 10 <= cnt < 20:
            for i in range(100):
                tmp = np.array([[random.randint(2, 8), random.randint(5, 7), random.randint(4, 10), random.randint(5, 6), random.randint(2, 6)]])
                user_preferences.append(tmp)
        elif 20 <= cnt < 30:
            for i in range(100):
                tmp = np.array([[random.randint(3, 6), random.randint(0, 10), random.randint(2, 8), random.randint(0, 10), random.randint(1, 5)]])
                user_preferences.append(tmp)
        elif 30 <= cnt < 40:
            for i in range(100):
                tmp = np.array([[random.randint(2, 10), random.randint(4, 6), random.randint(2, 6), random.randint(1, 7), random.randint(0, 10)]])
                user_preferences.append(tmp)
        elif 40 <= cnt < 50:
            for i in range(100):
                tmp = np.array([[random.randint(5, 8), random.randint(2, 10), random.randint(4, 7), random.randint(2, 10), random.randint(1, 5)]])
                user_preferences.append(tmp)
        else:
            for i in range(100):
                tmp = np.array([[random.randint(0, 10), random.randint(0, 10), random.randint(0, 10), random.randint(0, 10), random.randint(0, 10)]])
                user_preferences.append(tmp)
        cnt += 1
        
        distance_indice = []
        distance_from_user_preferences = []


        for user in user_preferences:
            distance_indice.append(knn.kneighbors(user))
            distance_from_user_preferences.append(knn.kneighbors(user)[0])
            

        # for arg in range(len(distance_indice)):
        #     print(f"유저 {arg + 1}에 대한 KNN:")
        #     for index_array in distance_indice[arg][1]:
        #         for index in index_array:
        #             print(f'{labels[index]}, ', end="")
        #         print("")



        # inverse_distance = []
        probabilitie = []
        for distance in distance_from_user_preferences:
            instance = 1 / (distance[0] + 1e-10)
            # inverse_distance.append(instance)
            probabilitie.append(instance / sum(instance))


        # 동별 combined_scores 를 담을 딕셔너리
        city_score = {}
        # Step 1: KNN 통합.
        # 기본 데이터 셋을 준비
        knn_results = {
            "덮밥": [],
            "전기구이통닭": [],
            '구황작물': [],
            '꼬치': [],
            '타코야끼': [],
            '타코 & 케밥': [],
            '분식': [],
            "빵": [],
            "디저트 & 카페": [],
            '기타': []
        }

        for idx in range(len(distance_indice)):
            for i, index_array in enumerate(distance_indice[idx][1]):
                for j, index in enumerate(index_array):
                    label = labels[index]
                    if label in knn_results:
                        knn_results[label].append(probabilitie[idx][j])




        # knn에 추가되지 않은 값은 0.01로 바꾼다.
        for key in knn_results:
            if not knn_results[key]:  # 키 값이 없을 때 0.01로 저장
                knn_results[key] = [0.01]  # Assign 디폴트 0.01 Assign시킨다.

        combined_scores = {item: sum(scores) / len(scores) if scores else 0.01 for item, scores in knn_results.items()}
        
   
        city_score[same] = combined_scores
        
        # print("Combined KNN Scores for Each Food Item:")
        # for item, score in city_score[same].items():
        #     print(f"{item}: {score:.2f}")
        
        # combined_scores 의 메뉴와 해당 메뉴에 대한 점수에 relative_value (하차인원비율) 을 곱한 값으로 갱신
        # 하차인원 비율에 대한 의존성이 너무 높으면 배율 조정
        
        # combined_scores : 특정 동에 대한 유저들의 점수 모음 셋
        # 앙상블
        
        # 여기서 동별 유저에 대한 combined_scores for문 으로 한번 씌우기
        
        final_scores = {item: (score * 3.33) + (relative_value * 0.03) for item, score in city_score[same].items()}
        
        
        # 점주가 파는 음식과 같은 값이 있다면 저장한다.
        if food_truck_manager_item in final_scores:
            food_truck_manager_item_scores[same] = final_scores[food_truck_manager_item]
            

    # 동네에 담긴 리스트를 sorted 시켜준다.
    top_10_neighborhoods = sorted(food_truck_manager_item_scores.items(), key=lambda x: x[1], reverse=True)[:10]

    print(f"\n  {food_truck_manager_item}를 선호하는 10개의 동네를 추천 :")
    
    inner = {}
    for same, score in top_10_neighborhoods:
        print(f"{same}: {score:.8f}")
        inner[same] = f'{score:.8f}'
    result[food_truck_manager_item] = inner



with open("data.json", 'w', encoding="cp949") as f:    
    json.dump(result, f, indent='\t', ensure_ascii=False)
    
    
with open('data.json', 'r') as f:
    data = json.load(f)
    
# print(json.dumps(data, indent=4,ensure_ascii = False))
# 각 음식 종류와 해당 도시, 평가 점수를 데이터베이스에 삽입

# MySQL 연결 설정
mydb = mysql.connector.connect(
  host=os.environ.get("MYSQL_HOST"),
  user=os.environ.get("MYSQL_USER"),
  password=os.environ.get("MYSQL_PASSWORD"),
  database=os.environ.get("MYSQL_DATABASE")
)

# 커서 생성
mycursor = mydb.cursor()

for food, city_scores in data.items():
    for city, score in city_scores.items():
        # SQL 쿼리 작성
        sql = "INSERT INTO bigdata (food, city, score) VALUES (%s, %s, %s)"
        # 쿼리 실행
        mycursor.execute(sql, (food, city, score))

# 변경 사항 저장
mydb.commit()

# 연결 종료
mydb.close()



