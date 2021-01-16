  public void download(final String directory) {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {
                storageReference = firebaseStorage.getInstance().getReference(directory);
                rootref= FirebaseDatabase.getInstance().getReference(directory);
                rootref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(final DataSnapshot paper:dataSnapshot.getChildren()) {
                            Log.e("papername",paper.child("name").getValue().toString());
                            Log.e("info",paper.child("name").getValue().toString());
                            myref = storageReference.child(paper.child("name").getValue().toString()+".pdf");
                            myref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    downloadfiles(getContext(),paper.child("name").getValue().toString(), ".pdf", DIRECTORY_DOWNLOADS, url);
                                    Log.e("inside onSucces","");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Unable to download now, try later", Toast.LENGTH_SHORT).show();
                                    Log.e("inside onfailure", "");
                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else
            {
                requestPermissions((Activity) getContext(),new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
        else
        {
            storageReference = firebaseStorage.getInstance().getReference(directory);
            rootref= FirebaseDatabase.getInstance().getReference(directory);
            rootref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(final DataSnapshot paper:dataSnapshot.getChildren()) {
                        Log.e("papername",paper.child("name").getValue().toString());
                        Log.e("info",paper.child("name").getValue().toString());
                        myref = storageReference.child(paper.child("name").getValue().toString()+".pdf");
                        myref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                downloadfiles(getContext(),paper.child("name").getValue().toString(), ".pdf", DIRECTORY_DOWNLOADS, url);
                                Log.e("inside onSucces","");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Unable to download now, try later", Toast.LENGTH_SHORT).show();
                                Log.e("inside onfailure", "");
                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }