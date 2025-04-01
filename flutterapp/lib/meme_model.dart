class Meme {
  int? id;
  String title;
  String description;
  String imageUrl;
  String category;
  bool isLiked;

  Meme({
    this.id,
    required this.title,
    required this.description,
    required this.imageUrl,
    required this.category,
    this.isLiked = false,
  });

  factory Meme.fromMap(Map<String, dynamic> map) {
    return Meme(
      id: map['id'],
      title: map['title'],
      description: map['description'],
      imageUrl: map['imageUrl'],
      category: map['category'],
      isLiked: map['isLiked'] == 1,
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'imageUrl': imageUrl,
      'category': category,
      'isLiked': isLiked ? 1 : 0,
    };
  }
}
