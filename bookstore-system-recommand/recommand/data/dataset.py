# data/dataset.py

import pandas as pd
from sklearn.preprocessing import StandardScaler
from sqlalchemy import create_engine


def load_data():
    # 使用 SQLAlchemy 创建数据库连接
    engine = create_engine('mysql+pymysql://root:ty8023wc@localhost:3306/bookstore')

    # 查询数据：提取用户、书籍、购买记录和浏览时长
    query = """
    SELECT user_id, book_id, view_time
    FROM user_book_interaction
    """
    df = pd.read_sql(query, engine)

    # 标准化浏览时长
    scaler = StandardScaler()
    df['view_time'] = scaler.fit_transform(df[['view_time']])

    return df
