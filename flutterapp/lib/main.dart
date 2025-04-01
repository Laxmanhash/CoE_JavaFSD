import 'package:flutter/material.dart';
import 'meme_form.dart';
import 'meme_model.dart';
import 'meme_db.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Memes App',
      theme: ThemeData(primarySwatch: Colors.blue, brightness: Brightness.dark),
      home: MemeListScreen(),
    );
  }
}

class MemeListScreen extends StatefulWidget {
  @override
  _MemeListScreenState createState() => _MemeListScreenState();
}

class _MemeListScreenState extends State<MemeListScreen> {
  final MemeDatabase db = MemeDatabase(); 
  List<Meme> memes = [];
  String selectedCategory = "All";

  final List<String> categories = [
    "All",
    "Political Memes",
    "Dark Memes",
    "Daily Memes",
    "Just for Fun Memes",
  ];

  @override
  void initState() {
    super.initState();
    _loadMemes();
  }

  void _loadMemes() async {
    List<Meme> loadedMemes = await db.getMemes(category: selectedCategory);
    setState(() {
      memes = loadedMemes;
    });
  }

  void _addMeme(Meme meme) async {
    await db.insertMeme(meme);
    _loadMemes();
  }

  void _deleteMeme(int id) async {
    await db.deleteMeme(id);
    _loadMemes();
  }

  void _toggleLike(Meme meme) async {
    meme.isLiked = !meme.isLiked;
    await db.updateMeme(meme);
    _loadMemes();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Memes App'),
        actions: [
          DropdownButton<String>(
            value: selectedCategory,
            items:
                categories.map((String category) {
                  return DropdownMenuItem(
                    value: category,
                    child: Text(category),
                  );
                }).toList(),
            onChanged: (value) {
              setState(() {
                selectedCategory = value!;
                _loadMemes();
              });
            },
          ),
        ],
      ),
      body:
          memes.isEmpty
              ? Center(child: Text("No memes found in this category."))
              : ListView.builder(
                itemCount: memes.length,
                itemBuilder: (context, index) {
                  final meme = memes[index];
                  return Card(
                    margin: EdgeInsets.all(8.0),
                    child: Column(
                      children: [
                        ClipRRect(
                          borderRadius: BorderRadius.vertical(
                            top: Radius.circular(10),
                          ),
                          child: Image.network(
                            meme.imageUrl,
                            width: double.infinity,
                            height:
                                300, 
                            fit:
                                BoxFit
                                    .contain, 
                            errorBuilder:
                                (context, error, stackTrace) => Container(
                                  height: 200,
                                  color: Colors.grey[400],
                                  child: Center(
                                    child: Icon(Icons.broken_image, size: 50),
                                  ),
                                ),
                          ),
                        ),
                        ListTile(
                          title: Text(meme.title),
                          subtitle: Text(meme.description),
                          trailing: Row(
                            mainAxisSize: MainAxisSize.min,
                            children: [
                              IconButton(
                                icon: Icon(
                                  meme.isLiked
                                      ? Icons.favorite
                                      : Icons.favorite_border,
                                  color:
                                      meme.isLiked ? Colors.red : Colors.grey,
                                ),
                                onPressed: () => _toggleLike(meme),
                              ),
                              IconButton(
                                icon: Icon(Icons.delete, color: Colors.red),
                                onPressed: () => _deleteMeme(meme.id!),
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  );
                },
              ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.add),
        onPressed: () async {
          final meme = await Navigator.push<Meme>(
            context,
            MaterialPageRoute(builder: (context) => MemeFormScreen()),
          );
          if (meme != null) {
            _addMeme(meme);
          }
        },
      ),
    );
  }
}
