import 'package:momentum/momentum.dart';
import 'package:search_engine_project/Documens-controller.dart';

// View models are models which are associated to a View.
// Don't confuse these with the ViewModels of MVVM architecture.
// These are simply models which hold data for a view. The actions/events are still under the domain of a controller.

class DocumentsModel extends MomentumModel<DocumentsController> {
  final searchDocuments;
  final isLoading;

  DocumentsModel(
    DocumentsController controller, {
    this.searchDocuments,
    this.isLoading,
  }) : super(controller);

  @override
  void update({
    List searchDocuments,
    bool isLoading,
  }) {
    DocumentsModel(
      controller,
      searchDocuments: searchDocuments ?? this.searchDocuments,
      isLoading: isLoading ?? this.isLoading,
    ).updateMomentum();
  }
}
