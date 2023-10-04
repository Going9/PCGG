import random
import pandas as pd
import statistics
# import matplotlib.pyplot as plt
# %matplotlib inline
from sklearn.metrics.pairwise import cosine_similarity
import operator


def similar_users(user_id, matrix, k=10):
    # 현재 유저에 대한 데이터프레임 만들기
    # matrix의 index = user_id -> 현재 1명 유저에 대한 평가 정보 찾기
    user = matrix[matrix.index == user_id]

    # matrix index 값이 user_id와 다른가?
    # 일치하지 않는 값들은 other_users
    other_users = matrix[matrix.index != user_id]

    # 대상 user, 다른 유저와의 cosine 유사도 계산
    # list 변환
    similarities = cosine_similarity(user, other_users)[0].tolist()

    # 다른 사용자의 인덱스 목록 생성
    other_users_list = other_users.index.tolist()

    # 인덱스/유사도로 이뤄진 딕셔너리 생성
    # dict(zip()) -> {'other_users_list1': similarities, 'other_users_list2': similarities}
    user_similarity = dict(zip(other_users_list, similarities))

    # 딕셔너리 정렬
    # key=operator.itemgetter(1) -> 오름차순 정렬 -> reverse -> 내림차순
    user_similarity_sorted = sorted(user_similarity.items(), key=operator.itemgetter(1))
    user_similarity_sorted.reverse()

    # 가장 높은 유사도 k개 정렬하기
    top_users_similarities = user_similarity_sorted[:k]
    users = [i[0] for i in top_users_similarities]

    return users


def recommend_item(user_index, similar_user_indices, matrix, items=5):
    # 유저와 비슷한 유저 가져오기
    similar_users = matrix[matrix.index.isin(similar_user_indices)]
    # 비슷한 유저 평균 계산 # row 계산
    similar_users = similar_users.mean(axis=0)
    # dataframe 변환 : 정렬/필터링 용이
    similar_users_df = pd.DataFrame(similar_users, columns=['user_similarity'])

    # 현재 사용자의 벡터 가져오기 : matrix = rating_matrix(pivot table)
    user_df = matrix[matrix.index == user_index]

    # 현재 사용자의 평가 데이터 정렬
    user_df_transposed = user_df.transpose()

    # 컬럼명 변경 48432 -> rating
    user_df_transposed.columns = ['rating']

    # 미시청 콘텐츠는 rating 0로 바꾸어 준다. remove any rows without a 0 value. Anime not watched yet
    user_df_transposed = user_df_transposed[user_df_transposed['rating'] == 0]

    # 평점을 매기지 않은 주변기기 목록 만들기
    device_unseen = user_df_transposed.index.tolist()

    # 안본 콘텐츠 필터링
    similar_users_df_filtered = similar_users_df[similar_users_df.index.isin(device_unseen)]

    # 평균값을 기준으로 내림차순 정렬
    similar_users_df_ordered = similar_users_df_filtered.sort_values(by=['user_similarity'], ascending=False)

    # 상위 10개 값 가져오기
    # items = 10
    top_n_anime = similar_users_df_ordered.head(items)
    top_n_anime_indices = top_n_anime.index.tolist()

    return top_n_anime_indices
