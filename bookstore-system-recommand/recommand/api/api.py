import os

import torch
from flask import Flask, request, jsonify

from ..model.recommendation_model import RecommendationModel

app = Flask(__name__)

# 加载训练好的模型
model = RecommendationModel(num_users=6, num_books=6, embedding_dim=50)
print("model", model)
# 获取模型文件的绝对路径
model_path = os.path.join(os.path.dirname(__file__), '..', 'recommendation_model.pth')

# 打印路径以确认
print("Model path:", model_path)

# 加载模型
model.load_state_dict(torch.load(model_path))
model.eval()  # 设置为评估模式
# 打印出模型的权重（参数）
for name, param in model.state_dict().items():
    print(f"{name}: {param.shape}")

# 推荐书籍的方法
def recommend_books(user_id_tensor, top_k=6):
    with torch.no_grad():  # 禁用梯度计算
        print("user_id_tensor", user_id_tensor)
        # 获取用户的嵌入向量
        user_emb = model.user_embedding(user_id_tensor)
        print("user_emb", user_emb)
        # 获取所有书籍的嵌入向量
        book_emb = model.book_embedding.weight
        print("book_emb", book_emb)
        # 计算用户和所有书籍的相似度（例如使用点积）
        scores = torch.matmul(user_emb, book_emb.T)
        print("scores", scores)
        # 获取得分最高的 top_k 本书
        _, top_k_indices = torch.topk(scores, top_k)
        top_k_indices = top_k_indices + 1
        return top_k_indices.squeeze().tolist()


# 推荐接口
@app.route('/recommend', methods=['GET'])
def recommend():
    # 获取用户ID作为查询参数
    user_id = request.args.get('user_id', type=int)

    if user_id is None:
        return jsonify({'error': 'Missing user_id parameter'}), 400
    user_id = user_id - 1
    # 将用户 ID 转为 Tensor
    user_id_tensor = torch.tensor([user_id], dtype=torch.long)
    print("user_id_tensor", user_id_tensor)
    # 获取 top_k 推荐的书籍
    top_k_books = recommend_books(user_id_tensor, top_k=5)
    print("top_k_books", top_k_books)
    # 返回推荐的书籍 ID
    return jsonify({'user_id': user_id + 1, 'recommended_books': top_k_books})


if __name__ == '__main__':
    app.run(debug=True)
