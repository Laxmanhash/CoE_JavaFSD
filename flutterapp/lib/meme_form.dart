import 'package:flutter/material.dart';
import 'meme_model.dart';

class MemeFormScreen extends StatefulWidget {
  @override
  _MemeFormScreenState createState() => _MemeFormScreenState();
}

class _MemeFormScreenState extends State<MemeFormScreen> {
  final _formKey = GlobalKey<FormState>();
  String title = '';
  String description = '';
  String imageUrl = '';
  String category = 'Political Memes';

  final List<String> categories = [
    "Political Memes",
    "Dark Memes",
    "Daily Memes",
    "Just for Fun Memes",
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Add Meme')),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextFormField(
                decoration: InputDecoration(labelText: 'Meme Title'),
                validator: (value) => value!.isEmpty ? 'Enter a title' : null,
                onSaved: (value) => title = value!,
              ),
              TextFormField(
                decoration: InputDecoration(labelText: 'Meme Description'),
                validator:
                    (value) => value!.isEmpty ? 'Enter a description' : null,
                onSaved: (value) => description = value!,
              ),
              TextFormField(
                decoration: InputDecoration(labelText: 'Image URL'),
                validator:
                    (value) => value!.isEmpty ? 'Enter an image URL' : null,
                onSaved: (value) => imageUrl = value!,
              ),
              DropdownButtonFormField<String>(
                value: category,
                items:
                    categories.map((String category) {
                      return DropdownMenuItem(
                        value: category,
                        child: Text(category),
                      );
                    }).toList(),
                onChanged: (value) => setState(() => category = value!),
              ),
              SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    _formKey.currentState!.save();
                    Navigator.pop(
                      context,
                      Meme(
                        title: title,
                        description: description,
                        imageUrl: imageUrl,
                        category: category,
                      ),
                    );
                  }
                },
                child: Text('Save Meme'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
