import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'dart:convert' as convert;
import 'package:http/http.dart' as http;
import 'package:momentum/momentum.dart';
import 'package:search_engine_project/Documents-model.dart';
import 'package:search_engine_project/link-component.dart';
import 'package:url_launcher/url_launcher.dart';

// This is a controller for a view and a model.
// Keep all your business logic here.
// Trigger your API calls here and set the data in the ViewModels from here as well.
// Add .controller suffix to denote association with a model and a view.

class DocumentsController extends MomentumController<DocumentsModel> {
  @override
  DocumentsModel init() {
    return DocumentsModel(this, searchDocuments: [], isLoading: false);
  }

  Future<void> getSearchDocuments(searchInput, context) async {
    model.update(isLoading: true);
    var url =
        Uri.parse("http://10.0.2.2:49480/documents/" + searchInput.toString());
    var response = await http.get(
      url,
      headers: <String, String>{
        'Content-Type': 'application/json',
      },
    );
    print(response.statusCode);
    print(response.body);
    if (response.statusCode == 200) {
      print(response.body);
      List Documents = [];
      model.update(searchDocuments: Documents);
      var jsonResponse = convert.jsonDecode(response.body) as List<dynamic>;
      for (var responseItem in jsonResponse) {
        print(responseItem["name"]);
        final field = LinkViewComponent(
          url: responseItem["url"],
          description: responseItem["description"],
          title: responseItem['title'],
        );
        Documents.add(field);
      }
      model.update(searchDocuments: Documents);
      model.update(isLoading: false);
    } else {
      model.update(isLoading: false);
      print("error");
    }
  }
}
